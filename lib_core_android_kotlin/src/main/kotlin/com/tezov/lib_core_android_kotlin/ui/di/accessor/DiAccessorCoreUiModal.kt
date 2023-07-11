/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.di.accessor

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalPagesBundle
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog.Dialog
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeCoreUiPage
import com.tezov.lib_core_android_kotlin.ui.di.component.ComponentCoreUiModal
import com.tezov.lib_core_android_kotlin.ui.di.component.DaggerComponentCoreUiModal_EntryPoint
import javax.inject.Inject

@ScopeCoreUiPage
class DiAccessorCoreUiModal @Inject protected constructor() :
    DiAccessor<ComponentCoreUiModal.EntryPoint>() {

    companion object {
        @Composable
        operator fun invoke() =
            DiAccessorCoreUiPage().with(
                requester = this,
                key = LocalPagesBundle.last().current
            ).accessorDialog()

        @Composable
        operator fun invoke(requester: Dialog<*, *>) =
            DiAccessorCoreUiPage().with(
                key = LocalPagesBundle.last().current,
            ).accessorDialog().with(
                key = requester
            )
    }

    @Composable
    override fun create() = DaggerComponentCoreUiModal_EntryPoint
        .factory()
        .create(
            DiAccessorCoreUiPage().with(
                key = LocalPagesBundle.last().current
            )
        )

}