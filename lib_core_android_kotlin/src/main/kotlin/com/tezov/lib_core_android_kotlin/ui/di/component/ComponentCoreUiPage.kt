/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.component

import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiModal
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeCoreUiPage
import dagger.Component

object ComponentCoreUiPage {

    @ScopeCoreUiPage
    @Component(
        dependencies = [ComponentCoreUiActivity.EntryPoint::class],
        modules = []
    )
    interface EntryPoint {

        @Component.Factory
        interface Factory {
            fun create(componentCoreActivity: ComponentCoreUiActivity.EntryPoint): EntryPoint
        }

        fun accessorDialog(): DiAccessorCoreUiModal

    }


}

