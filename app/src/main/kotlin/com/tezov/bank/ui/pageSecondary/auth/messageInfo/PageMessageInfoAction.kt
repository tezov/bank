

package com.tezov.bank.ui.pageSecondary.auth.messageInfo

import androidx.compose.runtime.Composable
import com.tezov.lib_adr_sdk_core.navigation.NavigationController
import com.tezov.lib_adr_sdk_core.navigation.NavigationRouteManager.NotImplemented
import com.tezov.lib_adr_sdk_core.ui.compositionTree.page.PageAction

class PageMessageInfoAction private constructor(
    private val navigationController: NavigationController,
) : PageAction<PageMessageInfoState> {

    companion object {
        @Composable
        fun create(
            navigationController: NavigationController,
        ) = PageMessageInfoAction(
            navigationController = navigationController,
        )
    }

    fun onClickMessage(index: Int) {
        navigationController.requestNavigate(NotImplemented("click message $index"), this)
    }

}