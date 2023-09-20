

package com.tezov.bank.ui.pageMain.auth.payment

import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.Route.NotImplemented
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageAction

class PagePaymentAction private constructor(
    private val navigationController: NavigationController,
) :
    PageAction<PagePaymentState>() {


    companion object {

        fun create(
            navigationController: NavigationController
        ) = PagePaymentAction(
            navigationController = navigationController,
        )
    }

    fun onClickCardsSmall(index: Int) {
        navigationController.requestNavigate(NotImplemented("click card small $index"), this)
    }

    fun onClickCardsLarge(index: Int) {
        navigationController.requestNavigate(NotImplemented("click card large $index"), this)
    }


}