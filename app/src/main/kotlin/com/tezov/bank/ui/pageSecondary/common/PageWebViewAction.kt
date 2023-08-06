

package com.tezov.bank.ui.pageSecondary.common

import com.tezov.lib_adr_sdk_core.navigation.NavigationController
import com.tezov.lib_adr_sdk_core.ui.compositionTree.page.PageAction

class PageWebViewAction private constructor(
    private val navigationController: NavigationController,
) :
    PageAction<PageWebViewState> {


    companion object {

        fun create(
            navigationController: NavigationController
        ) = PageWebViewAction(
            navigationController = navigationController,
        )
    }

    fun onClickClose() {
        navigationController.requestNavigateBack(this)
    }

}