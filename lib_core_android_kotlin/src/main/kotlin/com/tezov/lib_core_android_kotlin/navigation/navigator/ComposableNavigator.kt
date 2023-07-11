/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 16:53
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation.navigator

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.tezov.lib_core_android_kotlin.navigation.NavHost
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull
import com.tezov.lib_core_kotlin.type.ref.WR
import kotlin.properties.Delegates

open class GraphEntry(
    entry: NavBackStackEntry,
) {
    var isForeground by Delegates.notNull<Boolean>()
        internal set
    var isTransitioning by Delegates.notNull<Boolean>()
        internal set

    init {
        //TODO: extract data from entry useful to page
    }

}

internal abstract class ComposableNavigator : Navigator<ComposableNavigator.Content>() {

    internal companion object {

        inline val NavBackStackEntry.content
            get() = (destination as? Content)
                ?: throw IllegalStateException("destination is not a content")

        inline val NavBackStackEntry.navigator
            get() = content.navigator
                ?: throw IllegalStateException("navigator can't be null")
    }

    @Composable
    open fun composePrepare(
        navHost: NavHost,
        entry: NavBackStackEntry,
    ) {

    }

    open fun updateCompletion(
        navHost: NavHost,
        entry: NavBackStackEntry,
    ) {
        val entryContent = entry.content
        if (!entryContent.isVisible || entryContent.requestComplete
            || navHost.isLastEntry(entry)
        ) return
        if (entryContent.foregroundId.isNotNull) {
            navHost.nextEntryOf(entry)?.takeIf { nextEntry ->
                val nextEntryContent = nextEntry.content
                nextEntryContent.isVisible && !nextEntryContent.requestComplete && entryContent.foregroundId == nextEntry.id
            }?.let { return }
        }
        entryContent.requestComplete = true
    }

    fun completeIfRequested(
        entry: NavBackStackEntry,
    ) {
        val entryContent = entry.content
        if (entryContent.requestComplete && entryContent.isAnimationCompleted) {
            entryContent.requestComplete = false
            entryContent.isVisible = false
            state.markTransitionComplete(entry)
        }
    }

    @Composable
    fun compose(
        saveableStateHolder: SaveableStateHolder,
        navHost: NavHost,
        entry: NavBackStackEntry,
    ) {
        val entryContent = entry.content
        if (!entryContent.isVisible) return
        kotlin.runCatching {
            saveableStateHolder.SaveableStateProvider(entryContent.route ?: entry.id) {
                val graphEntry = remember { createGraphEntry(entry) }.apply {
                    this.isForeground = navHost.isLastEntry(entry)
                    this.isTransitioning = !entryContent.isAnimationCompleted
                }
                entryContent.compose(graphEntry = graphEntry)
            }
        }.onFailure {
            //TODO: SaveableStateProvider can crash on monkey bottom bar test. Key is used multiple time
            //-navHost entries double ?
            //-failed to lock/unlock navigate ?
        }
    }

    open fun createGraphEntry(entry: NavBackStackEntry) = GraphEntry(entry = entry)

    final override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) {
        entries.forEach { state.pushWithTransition(it) }
    }

    final override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        popUpTo.content.clear()
        state.popWithTransition(popUpTo, savedState)
    }

    open class Content(
        navigator: ComposableNavigator,
        val animation: NavigationAnimation.Config? = null,
        internal val content: @Composable BoxScope.(GraphEntry) -> Unit
        //TODO Make ContentComposable class to make it possible to split animation (Header, Body, Footer)
    ) : NavDestination(navigator) {
        private val _navigator = WR(navigator)
        val navigator get() = _navigator.value

        var requestComplete: Boolean = false
        var isVisible: Boolean = false
        var modifierAnimation: NavigationAnimation.ModifierAnimation = NavigationAnimation.None()
        val isAnimationCompleted get() = modifierAnimation is NavigationAnimation.None

        var backgroundId: String? = null
        var foregroundId: String? = null

        open fun clear() {
            requestComplete = false
            isVisible = false
            modifierAnimation = NavigationAnimation.None()
            backgroundId = null
            foregroundId = null
        }

        @Composable
        open fun compose(graphEntry: GraphEntry) {
            Box(
                modifier = modifierAnimation
            ) {
                content(graphEntry)
            }
        }

    }

}




