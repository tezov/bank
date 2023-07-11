/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import com.tezov.lib_core_android_kotlin.animation.AnimationProgress
import com.tezov.lib_core_android_kotlin.animation.AnimationProgress.Companion.updateAnimationProgress
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation.Config.Type
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation.Fade.fade
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation.Slide.Horizontal.slideHorizontal
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation.Slide.Vertical.slideVertical
import com.tezov.lib_core_android_kotlin.navigation.navigator.ComposableNavigator
import com.tezov.lib_core_android_kotlin.navigation.navigator.ComposableNavigator.Companion.content
import com.tezov.lib_core_android_kotlin.navigation.navigator.ComposableNavigator.Companion.navigator
import com.tezov.lib_core_kotlin.async.notifier.observer.value.ObserverValue
import com.tezov.lib_core_kotlin.extension.ExtensionList.NULL_INDEX
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NavHost(
    navController: NavigationController,
    route: NavigationRouteManager.Route? = null,
    startRoute: NavigationRouteManager.Route,
    animationConfig: NavigationAnimation.Config = NavigationAnimation.Config(),
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        animationConfig = animationConfig,
        graph = remember(route?.path, startRoute.path, builder) {
            navController.navHostController.createGraph(
                startDestination = startRoute.path,
                route = route?.path,
                builder = builder,
            )
        },
    )
}

@Composable
private fun NavHost(
    navController: NavigationController,
    animationConfig: NavigationAnimation.Config,
    graph: NavGraph,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "NavHost requires a ViewModelStoreOwner to be provided via LocalViewModelStoreOwner"
    }
    val navHostController = navController.navHostController
    navHostController.setLifecycleOwner(lifecycleOwner)
    navHostController.setViewModelStore(viewModelStoreOwner.viewModelStore)
    navHostController.graph = graph
    val saveableStateHolder = rememberSaveableStateHolder()
    remember {
        NavHost(
            saveableStateHolder = saveableStateHolder,
            navController = navController,
            animationConfig = animationConfig
        )
    }.also {
        it.compose(
            backQueue = navController.backQueue,
            visibleEntries = navHostController.visibleEntries,
        )
    }
}

internal class NavHost(
    private val saveableStateHolder: SaveableStateHolder,
    private val navController: NavigationController,
    private val animationConfig: NavigationAnimation.Config,
) {
    private val entries: ArrayDeque<NavBackStackEntry> = ArrayDeque()
    private var lastEntryId: String? = null
    private var isLastEntryHasChanged: Boolean = false

    val isIdle get() = navController.isIdle

    val isNavigatingBack get() = navController.isNavigatingBack

    fun isLastEntry(entry: NavBackStackEntry) = entry.id == lastEntryId

    private val indexOfLastEntry get() = entries.indexOfLast { it.id == lastEntryId }

    val lastEntry get() = entries.last { it.id == lastEntryId }

    val lastEntries
        get() = when {
            entries.size >= 2 -> {
                when {
                    !navController.isIdle && navController.isNavigatingBack -> {
                        entries.subList(0, indexOfLastEntry + 1).filter { it.content.isVisible }
                    }

                    else -> {
                        entries.subList(indexOfLastEntry, entries.size)
                            .filter { it.content.isVisible }
                    }
                }
            }

            else -> {
                null
            }
        }

    val priorEntries
        get() = when {
            entries.size >= 2 -> {
                when {
                    !navController.isIdle && navController.isNavigatingBack -> {
                        entries.subList(indexOfLastEntry + 1, entries.size)
                            .filter { it.content.isVisible }
                    }

                    else -> {
                        entries.subList(0, indexOfLastEntry).filter { it.content.isVisible }
                    }
                }
            }

            else -> {
                null
            }
        }

    fun nextEntryOf(entry: NavBackStackEntry) =
        entries.indexOf(entry).takeIf { it != NULL_INDEX }?.let { index ->
            entries.getOrNull(index + 1)
        }

    fun previousEntryOf(entry: NavBackStackEntry) =
        entries.indexOf(entry).takeIf { it != NULL_INDEX }?.let { index ->
            entries.getOrNull(index - 1)
        }

    @Composable
    fun compose(
        backQueue: StateFlow<List<NavBackStackEntry>>,
        visibleEntries: StateFlow<List<NavBackStackEntry>>,
    ) {
        val backQueueState by remember(this) { backQueue }.collectAsState(emptyList())
        val visibleEntriesState by remember(this) { visibleEntries }.collectAsState(emptyList())

        (lastEntryId ?: let {
            isLastEntryHasChanged = false
            lastEntryId = visibleEntriesState.lastOrNull()?.id
        })
        updateEntries(
            backQueueState = backQueueState,
            visibleEntriesState = visibleEntriesState,
        )
        if (entries.isEmpty()) return
        for (entry in entries) {
            entry.navigator.composePrepare(
                navHost = this@NavHost,
                entry = entry
            )
        }
        for (entry in entries.asReversed()) {
            entry.navigator.updateCompletion(
                navHost = this@NavHost,
                entry = entry
            )
        }
        updateTransition()
        for (entry in entries) {
            key(entry.id) {
                entry.navigator.apply {
                    completeIfRequested(entry)
                    compose(
                        saveableStateHolder = saveableStateHolder,
                        navHost = this@NavHost,
                        entry = entry,
                    )
                }
            }
        }
    }

    @Composable
    private fun updateEntries(
        backQueueState: List<NavBackStackEntry>,
        visibleEntriesState: List<NavBackStackEntry>,
    ) {
        val visibleEntriesStateDistinct = visibleEntriesState.distinctBy { it.id }
        val common = entries.intersect(visibleEntriesStateDistinct)
        common.forEach { commonEntry ->
            commonEntry.content.takeIf { !it.isVisible }?.let { content ->
                content.isVisible = true
            }
        }
        val diff = entries.subtract(visibleEntriesStateDistinct)
        diff.forEach { diffEntry ->
            if (!backQueueState.contains(diffEntry)) {
                entries.remove(diffEntry)
            }
        }
        if (!isNavigatingBack) {
            val new = visibleEntriesStateDistinct.subtract(entries)
            new.forEach { newEntry ->
                newEntry.content.isVisible = true
                entries.addLast(newEntry)
            }
        }
        when {
            !navController.isIdle && navController.isNavigatingBack && entries.size >= 2 -> {
                entries.subList(0, entries.size - 1).last { it.content.isVisible }
            }

            else -> {
                entries.lastOrNull()
            }
        }?.let {
            if (it.id != lastEntryId) {
                lastEntryId = it.id
                isLastEntryHasChanged = true
            }
        }
    }

    private fun ComposableNavigator.Content.updateTransition(
        transition: AnimationProgress,
        animationConfig: NavigationAnimation.Config,
        directionContent: NavigationAnimation.Direction.Content,
    ) {
        val type = when (directionContent) {
            NavigationAnimation.Direction.Content.Enter -> when {
                isNavigatingBack -> animationConfig.enter.pop
                else -> animationConfig.enter.push
            }

            NavigationAnimation.Direction.Content.Exit -> when {
                isNavigatingBack -> animationConfig.exit.pop
                else -> animationConfig.exit.push
            }
        }
        when (type) {
            is Type.None -> {
                modifierAnimation = NavigationAnimation.None()
            }

            is Type.Fade -> {
                modifierAnimation = transition.fade(
                    config = type,
                    directionContent = directionContent,
                )
            }

            is Type.SlideHorizontal -> {
                modifierAnimation = transition.slideHorizontal(
                    config = type,
                    directionNav = when (isNavigatingBack) {
                        true -> NavigationAnimation.Direction.Nav.Pop
                        false -> NavigationAnimation.Direction.Nav.Push
                    },
                    directionContent = directionContent,
                )
            }

            is Type.SlideVertical -> {
                modifierAnimation = transition.slideVertical(
                    config = type,
                    directionNav = when (isNavigatingBack) {
                        true -> NavigationAnimation.Direction.Nav.Pop
                        false -> NavigationAnimation.Direction.Nav.Push
                    },
                    directionContent = directionContent,
                )
            }
        }
    }

    @Composable
    private fun updateTransition() {
        if (!isLastEntryHasChanged) {
            navController.unlockNavigate()
            return
        }
        val transition = updateAnimationProgress()
        val lastEntries = remember { lastEntries } ?: return
        val priorEntries = remember { priorEntries } ?: run {
            navController.unlockNavigate()
            return
        }
        val animationConfigResolved = if (isNavigatingBack) {
            priorEntries.last().content.animation
        } else {
            lastEntries.last().content.animation
        } ?: animationConfig
        priorEntries.forEach {
            it.content.updateTransition(
                transition = transition,
                animationConfig = animationConfigResolved,
                directionContent = NavigationAnimation.Direction.Content.Exit,
            )
        }
        lastEntries.forEach {
            it.content.updateTransition(
                transition = transition,
                animationConfig = animationConfigResolved,
                directionContent = NavigationAnimation.Direction.Content.Enter,
            )
        }
        if (transition.isIdle) {
            transition.register(object : ObserverValue<Boolean>(this) {
                override fun onComplete(value: Boolean) {
                    unsubscribe()
                    isLastEntryHasChanged = false
                    priorEntries.forEach { entry ->
                        entry.updateCompletion()
                    }
                    lastEntries.forEach { entry ->
                        entry.updateCompletion()
                    }
                    navController.unlockNavigate()
                }

                fun NavBackStackEntry.updateCompletion() =
                    this.content.apply {
                        val navHost = this@NavHost
                        val entry = this@updateCompletion
                        modifierAnimation = NavigationAnimation.None()
                        navigator?.apply {
                            updateCompletion(
                                navHost = navHost,
                                entry = entry
                            )
                            completeIfRequested(entry)
                        }
                    }

            })
        }
        transition.start()
    }


}





