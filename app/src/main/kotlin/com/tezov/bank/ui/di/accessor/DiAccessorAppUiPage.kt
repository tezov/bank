

package com.tezov.bank.ui.di.accessor

import androidx.compose.runtime.Composable
import com.tezov.bank.ui.di.component.ComponentAppUiPage
import com.tezov.bank.ui.di.component.DaggerComponentAppUiPage_EntryPoint
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.Activity.Companion.LocalPagesBundle
import com.tezov.lib_adr_app_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_app_core.ui.di.accessor.DiAccessor
import com.tezov.lib_adr_app_core.ui.di.accessor.DiAccessorCoreUiPage
import com.tezov.bank.ui.di.annotation.scope.ScopeAppUiActivity
import javax.inject.Inject

@ScopeAppUiActivity
class DiAccessorAppUiPage @Inject protected constructor() :
    DiAccessor<ComponentAppUiPage.EntryPoint>() {

    companion object {
        @Composable
        operator fun invoke() =
            DiAccessorAppUiActivity().with(
                requester = this,
                key = LocalActivity.current
            ).accessorPage()

        @Composable
        operator fun invoke(requester: Page<*, *>) =
            DiAccessorAppUiActivity().with(
                key = LocalActivity.current
            ).accessorPage().with(
                key = requester
            )
    }

    @Composable
    override fun create() = DaggerComponentAppUiPage_EntryPoint
        .factory()
        .create(
            DiAccessorCoreUiPage().with(
                key = LocalPagesBundle.last().current
            ),
            DiAccessorAppUiActivity().with(
                key = LocalActivity.current
            )
        )

    @Composable
    override fun dispose(requester: Any, key: Key) =
        DiAccessorCoreUiPage().dispose(requester, key) or super.dispose(requester, key)

}