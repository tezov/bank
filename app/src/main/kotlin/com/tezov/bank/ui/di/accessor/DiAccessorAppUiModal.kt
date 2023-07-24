

package com.tezov.bank.ui.di.accessor

import androidx.compose.runtime.Composable
import com.tezov.bank.ui.di.component.ComponentAppUiModal
import com.tezov.bank.ui.di.component.DaggerComponentAppUiActivity_EntryPoint
import com.tezov.bank.ui.di.component.DaggerComponentAppUiModal_EntryPoint
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog.Dialog
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessor
import com.tezov.lib_core_android_kotlin.ui.di.accessor.DiAccessorCoreUiModal
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeAppUiPage
import javax.inject.Inject

@ScopeAppUiPage
class DiAccessorAppUiModal @Inject protected constructor() :
    DiAccessor<ComponentAppUiModal.EntryPoint>() {

    companion object {
        @Composable
        operator fun invoke() =
            DiAccessorAppUiPage().with(
                requester = this,
                key = Activity.LocalPagesBundle.last().current
            ).accessorDialog()

        @Composable
        operator fun invoke(requester: Dialog<*, *>) =
            DiAccessorAppUiPage().with(
                key = Activity.LocalPagesBundle.last().current,
            ).accessorDialog().with(
                key = requester
            )
    }

    @Composable
    override fun create() = DaggerComponentAppUiModal_EntryPoint
        .factory()
        .create(
            DiAccessorCoreUiModal().with(
                key = Page.LocalModalsBundle.last().current
            ),
            DiAccessorAppUiPage().with(
                key = Activity.LocalPagesBundle.last().current
            )
        )

    @Composable
    override fun dispose(requester: Any, key: Key) =
        DiAccessorCoreUiModal().dispose(requester, key) or super.dispose(requester, key)

}