/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.module


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.tezov.lib_core_android_kotlin.navigation.NavigationNotifier
import com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigation
import com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationAction
import com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationState
import com.tezov.lib_core_android_kotlin.navigation.top_app_bar.TopAppBar
import com.tezov.lib_core_android_kotlin.navigation.top_app_bar.TopAppBarAction
import com.tezov.lib_core_android_kotlin.navigation.top_app_bar.TopAppBarState
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheet
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetAction
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetState
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.Dialog
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogState
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.Snackbar
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarAction
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarState
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContext
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContextMap
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Inject

object ModuleCoreUiActivity {

    @Module
    class Provider {

        @ScopeCoreUiActivity
        @Provides
        fun provideNavigationNotifier() = NavigationNotifier()

    }

    @Module
    class MapperContext {

        @ScopeCoreUiActivity
        @Provides
        fun provideContextCore(
            state: State.ActivityCoreState,
            action: Action.ActivityCoreAction
        ) = ComposableContext(state, action)

        @ScopeCoreUiActivity
        @Provides
        fun provideContextSub(
            bottomSheet: ComposableContext<BottomSheetState, BottomSheetAction>,
            snackbar: ComposableContext<SnackbarState, SnackbarAction>,
            dialog: ComposableContext<DialogState, DialogAction>,
            bottomNavigation: ComposableContext<BottomNavigationState, BottomNavigationAction>,
            topAppBar: ComposableContext<TopAppBarState, TopAppBarAction>,
        ) = ComposableContextMap(
            BottomSheet::class.java to bottomSheet,
            Snackbar::class.java to snackbar,
            Dialog::class.java to dialog,
            BottomNavigation::class.java to bottomNavigation,
            TopAppBar::class.java to topAppBar,
        )


        @ScopeCoreUiActivity
        @Provides
        fun provideBottomSheetContext(
            state: State.BottomSheetState,
            action: Action.BottomSheetAction
        ) = ComposableContext(state, action)

        @ScopeCoreUiActivity
        @Provides
        fun provideSnackbarContext(
            state: State.SnackbarState,
            action: Action.SnackbarAction
        ) = ComposableContext(state, action)

        @ScopeCoreUiActivity
        @Provides
        fun provideDialogContext(
            state: State.DialogState,
            action: Action.DialogAction
        ) = ComposableContext(state, action)

        @ScopeCoreUiActivity
        @Provides
        fun provideBottomNavigationContext(
            state: State.BottomNavigationState,
            action: Action.BottomNavigationAction
        ) = ComposableContext(state, action)

        @ScopeCoreUiActivity
        @Provides
        fun provideTopNavigationContext(
            state: State.TopNavigationState,
            action: Action.TopNavigationAction
        ) = ComposableContext(state, action)

    }

    object Provided {

        @ScopeCoreUiActivity
        class CoroutineScope @Inject constructor() :
            ComposableWrapper<kotlinx.coroutines.CoroutineScope>() {
            @Composable
            override fun create() = rememberCoroutineScope()
        }

    }

    object State {

        @ScopeCoreUiActivity
        class ActivityCoreState @Inject constructor() :
            ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreState>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreState.create()
        }

        @ScopeCoreUiActivity
        class ScaffoldState @Inject constructor() :
            ComposableWrapper<androidx.compose.material.ScaffoldState>() {
            @Composable
            override fun create() = rememberScaffoldState()
        }

        @ScopeCoreUiActivity
        class SnackbarState @Inject constructor(private val scaffoldState: ScaffoldState) :
            ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarState>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarState.create(
                    snackbarHostState = scaffoldState.get().snackbarHostState
                )
        }

        @ScopeCoreUiActivity
        class BottomSheetState @Inject constructor() :
            ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetState>() {
            @OptIn(ExperimentalMaterialApi::class)
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetState.create()
        }

        @ScopeCoreUiActivity
        class DialogState @Inject constructor() :
            ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogState>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogState.create()
        }

        @ScopeCoreUiActivity
        class BottomNavigationState @Inject constructor() :
            ComposableWrapper<com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationState>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationState.create()
        }

        @ScopeCoreUiActivity
        class TopNavigationState @Inject constructor() : ComposableWrapper<TopAppBarState>() {
            @Composable
            override fun create() = TopAppBarState.create()
        }
    }

    object Action {

        @ScopeCoreUiActivity
        class ActivityCoreAction @Inject constructor(
            private val navigationController: NavigationController,
            private val navigationNotifier: NavigationNotifier,
        ) : ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreAction>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreAction.create(
                    navigationController = navigationController.get(),
                    navigationNotifier = navigationNotifier,
                )
        }

        @ScopeCoreUiActivity
        class SnackbarAction @Inject constructor(
            private val coroutineScope: Provided.CoroutineScope,
            private val scaffoldState: State.ScaffoldState,
        ) : ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarAction>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarAction.create(
                    coroutineScope = coroutineScope.get(),
                    hostState = scaffoldState.get().snackbarHostState,
                )
        }

        @ScopeCoreUiActivity
        class BottomSheetAction @Inject constructor(
            private val bottomSheetState: State.BottomSheetState,
        ) : ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetAction>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetAction.create(
                    bottomSheetState = bottomSheetState.get()
                )
        }

        @ScopeCoreUiActivity
        class DialogAction @Inject constructor(
            private val dialogState: State.DialogState,
        ) : ComposableWrapper<com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction.create(
                    dialogState = dialogState.get()
                )
        }

        @ScopeCoreUiActivity
        class BottomNavigationAction @Inject constructor(
            private val navigationController: NavigationController,
        ) : ComposableWrapper<com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationAction>() {
            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigationAction.create(
                    navigationController = navigationController.get()
                )
        }

        @ScopeCoreUiActivity
        class TopNavigationAction @Inject constructor(
            private val navigationController: NavigationController,
        ) : ComposableWrapper<TopAppBarAction>() {
            @Composable
            override fun create() = TopAppBarAction.create(
                navigationController = navigationController.get()
            )
        }

        @ScopeCoreUiActivity
        class NavigationController @Inject constructor(
            private val navigationNotifier: NavigationNotifier,
        ) : ComposableWrapper<com.tezov.lib_core_android_kotlin.navigation.NavigationController>() {

            @Composable
            override fun create() =
                com.tezov.lib_core_android_kotlin.navigation.NavigationController.create(
                    navigationNotifier = navigationNotifier
                )
        }

    }

}

