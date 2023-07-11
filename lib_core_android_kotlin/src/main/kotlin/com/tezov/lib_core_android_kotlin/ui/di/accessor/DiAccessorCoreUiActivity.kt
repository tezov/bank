/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.accessor

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalCoreApplication
import com.tezov.lib_core_android_kotlin.ui.di.component.ComponentCoreUiActivity
import com.tezov.lib_core_android_kotlin.ui.di.component.DaggerComponentCoreUiActivity_EntryPoint

class DiAccessorCoreUiActivity protected constructor() :
    DiAccessor<ComponentCoreUiActivity.EntryPoint>() {

    companion object {
        @Composable
        operator fun invoke() = (LocalCoreApplication.current).accessorCoreUi

        @Composable
        operator fun invoke(requester: Activity<*, *>) = (LocalCoreApplication.current).accessorCoreUi
    }

    @Composable
    override fun create() = DaggerComponentCoreUiActivity_EntryPoint
        .factory()
        .create()

    @Composable
    fun remember(requester: Activity<*, *>) {
        with(requester).apply {
            exposeCoreCoroutineScope().remember()
            exposeCoreScaffoldState().remember()
            exposeCoreNavigationController().remember()
            contextSubMap().remember()
            contextCore().remember()
        }
    }

}