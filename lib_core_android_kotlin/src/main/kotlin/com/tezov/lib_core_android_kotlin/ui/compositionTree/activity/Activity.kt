/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.compositionTree.activity

import androidx.compose.runtime.*
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.tezov.lib_core_android_kotlin.application.Application
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.type.primaire.DpSize
import com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityBase
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.Composition
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.Modal
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page.Companion.LocalModalsBundle
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessor
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_kotlin.async.notifier.observer.value.ObserverValue

interface Activity<S : ActivityState, A : ActivityAction<S>> : Composition<S, A>, DiAccessor.Key {

    override val diAccessorKeyId: Int
        get() = Activity.hashCode()

    companion object {
        val LocalLevel: ProvidableCompositionLocal<Int> = staticCompositionLocalOf {
            -1
        }
        val LocalCoreApplication: ProvidableCompositionLocal<Application> = staticCompositionLocalOf {
            error("not provided")
        }
        val LocalActivityBundle: ProvidableCompositionLocal<Bundle> = staticCompositionLocalOf {
            error("not provided")
        }
        val LocalActivity @Composable get() = LocalActivityBundle.current
        val LocalPagesBundle @Composable get() = LocalActivityBundle.current.pages

        data class Bundle(
            val current: Activity<*, *>,
            internal val _size: MutableState<DpSize> = mutableStateOf(DpSize((-1).dp, (-1).dp))
        ) {
            val pages by lazy { ArrayDeque<Page.Companion.Bundle>() }

            val size get() = _size.value
        }

    }

    @Composable
    fun invokeContent() {
        val bundle = remember { Bundle(this) }
        Layout(
            content = {
                CompositionLocalProvider(
                    LocalLevel provides 0,
                    LocalCoreApplication provides LocalContext.current.applicationContext as Application,
                    LocalActivityBundle provides bundle,
                ) {
                    DiAccessorCoreUiActivity().remember(this)
                    backPressListen()
                    lifecycleListen()
                    navigateExitListen()
                    content()
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }
            bundle._size.value = DpSize(constraints.maxWidth.toDp(), constraints.maxHeight.toDp())
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = 0)
                }
            }
        }
    }

    @Composable
    fun Activity<S, A>.content()

    @Composable
    override fun onBackPressedDispatch() {
        if (!onBackButtonPressed()) {
            val accessor = DiAccessorCoreUiActivity().with(key = this)
            val action = accessor.contextCore().action()
            if (!action.navigationController.onBackPressedDispatch()) {
                (LocalActivity.current as? ActivityBase<*,*>)?.finishAffinity()
            }
        }
    }

    @Composable
    fun onBackButtonPressed() = false

    @Composable
    private fun navigateExitListen() {
        val state = remember {
            mutableStateOf<NavigationController.Request?>(null)
        }
        val accessor = DiAccessorCoreUiActivity().with(key = this)
        val action = accessor.contextCore().action()
        onNavigateDispatch(state)
        DisposableEffect(Unit) {
            val subscription = action.navigationNotifier.register(object :
                ObserverValue<NavigationController.Request>(this@Activity) {
                override fun onComplete(
                    value: NavigationController.Request
                ) {
                    state.value = value
                }
            })
            onDispose {
                subscription.unsubscribe()
            }
        }
    }

    @Composable
    private fun onNavigateDispatch(state: MutableState<NavigationController.Request?>) {
        state.value?.let { request ->
            state.value = null
            dispatchToTop(
                modalBlock = { onExit(request); true },
                pageBlock = { onExit(request); true },
                activityBlock = { onExit(request) },
            )
        }
    }

    @Composable
    private fun dispatchToTop(
        modalBlock: @Composable Modal<*, *>.() -> Boolean,
        pageBlock: @Composable Page<*, *>.() -> Boolean,
        activityBlock: @Composable Activity<*, *>.() -> Unit
    ) {
        var canDispatch = true
        LocalPagesBundle.lastOrNull()?.current?.let { page ->
            Page.CompositionLocalProvider(page) {
                LocalModalsBundle.lastOrNull()?.current?.let { modal ->
                    Modal.CompositionLocalProvider(modal) {
                        canDispatch = !modalBlock(modal)
                    }
                }
                if (canDispatch) {
                    canDispatch = !pageBlock(page)
                }
            }
        }
        if (canDispatch) {
            activityBlock(this)
        }
    }

    @Composable
    fun onExit(request: NavigationController.Request) {
    }

    @Composable
    private fun lifecycleListen() {
        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
        val state = remember { mutableStateOf<Lifecycle.Event?>(null) }
        DisposableEffect(lifecycleOwner) {
            val lifecycle = lifecycleOwner.value.lifecycle
            val observer = LifecycleEventObserver { _, event ->
                state.value = event
            }
            lifecycle.addObserver(observer)
            onDispose {
                lifecycle.removeObserver(observer)
            }
        }
        onLifecycleDispatch(state)
    }

    @Composable
    private fun onLifecycleDispatch(state: MutableState<Lifecycle.Event?>) {
        state.value?.let {
            state.value = null
            when (it) {
                Lifecycle.Event.ON_RESUME -> {
                    onShow()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    onHide()
                }
                else -> null
            }
        }
    }

    @Composable
    fun onShow() {
    }

    @Composable
    fun onHide() {
    }

}