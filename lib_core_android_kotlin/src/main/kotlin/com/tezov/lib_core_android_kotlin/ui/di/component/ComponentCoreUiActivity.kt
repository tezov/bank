/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.component

import com.tezov.lib_core_android_kotlin.navigation.NavigationNotifier
import com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreAction
import com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreState
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiPage
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContext
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContextMap
import com.tezov.lib_core_android_kotlin.ui.di.module.ModuleCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.module.ModuleCoreUiActivity.Action
import com.tezov.lib_core_android_kotlin.ui.di.module.ModuleCoreUiActivity.State
import com.tezov.lib_core_android_kotlin.ui.di.module.ModuleCoreUiActivity.Provided
import dagger.Component

object ComponentCoreUiActivity {

    @ScopeCoreUiActivity
    @Component(
        dependencies = [],
        modules = [ModuleCoreUiActivity.Provider::class, ModuleCoreUiActivity.MapperContext::class]
    )
    interface EntryPoint : Exposer {
        @Component.Factory
        interface Factory {
            fun create(): EntryPoint
        }

        fun accessorPage(): DiAccessorCoreUiPage

        fun contextCore(): ComposableContext<ActivityCoreState, ActivityCoreAction>

        fun contextSubMap(): ComposableContextMap

    }

    interface Exposer {
        fun exposeCoreActivityState(): State.ActivityCoreState
        fun exposeCoreActivityAction(): Action.ActivityCoreAction

        fun exposeCoreBottomSheetState(): State.BottomSheetState
        fun exposeCoreBottomSheetAction(): Action.BottomSheetAction
        fun exposeCoreDialogState(): State.DialogState
        fun exposeCoreDialogAction(): Action.DialogAction
        fun exposeCoreSnackbarState(): State.SnackbarState
        fun exposeCoreSnackbarAction(): Action.SnackbarAction
        fun exposeCoreBottomNavigationState(): State.BottomNavigationState
        fun exposeCoreBottomNavigationAction(): Action.BottomNavigationAction
        fun exposeCoreTopNavigationState(): State.TopNavigationState
        fun exposeCoreTopNavigationAction(): Action.TopNavigationAction

        fun exposeCoreCoroutineScope(): Provided.CoroutineScope
        fun exposeCoreScaffoldState(): State.ScaffoldState
        fun exposeCoreNavigationController(): Action.NavigationController
        fun exposeCoreNavigationNotifier(): NavigationNotifier
    }
}
