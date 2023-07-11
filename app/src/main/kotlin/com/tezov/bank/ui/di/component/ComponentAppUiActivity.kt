/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.di.component

import com.tezov.bank.ui.activity.MainActivityAction
import com.tezov.bank.ui.activity.MainActivityState
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.di.module.ModuleAppUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeAppUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContext
import com.tezov.lib_core_android_kotlin.ui.di.component.ComponentCoreUiActivity
import dagger.Component

object ComponentAppUiActivity {

    @ScopeAppUiActivity
    @Component(
        dependencies = [ComponentCoreUiActivity.EntryPoint::class],
        modules = [ModuleAppUiActivity.MapperContext::class]
    )
    interface EntryPoint : ComponentCoreUiActivity.Exposer, Exposer {

        @Component.Factory
        interface Factory {
            fun create(
                componentCoreActivity: ComponentCoreUiActivity.EntryPoint,
            ): EntryPoint
        }

        fun accessorPage(): DiAccessorAppUiPage

        fun contextMain(): ComposableContext<MainActivityState, MainActivityAction>

    }

    interface Exposer {

        fun exposeAppActivityMainState(): ModuleAppUiActivity.State.ActivityMainState
        fun exposeAppActivityMainAction(): ModuleAppUiActivity.Action.ActivityMainAction
    }

}

