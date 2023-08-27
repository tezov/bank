

package com.tezov.bank.ui.pageMain.auth.help

import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.Route.NotImplemented
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageAction

class PageHelpAction private constructor(
    private val navigationController: NavigationController,
) :
    PageAction<PageHelpState> {


    companion object {

        fun create(
            navigationController: NavigationController
        ) = PageHelpAction(
            navigationController = navigationController,
        )
    }


    fun onClickEmergencies(index: Int) {
        navigationController.requestNavigate(NotImplemented("click emergency $index"), this)
    }

    fun onClickPaymentModes(index: Int) {
        navigationController.requestNavigate(NotImplemented("click payment $index"), this)

    }

    fun onClickConfigurations(index: Int) {
        navigationController.requestNavigate(NotImplemented("click configuration $index"), this)
    }

    fun onClickAccounts(index: Int) {
        navigationController.requestNavigate(NotImplemented("click account $index"), this)
    }

    fun onClickMisc(index: Int) {
        navigationController.requestNavigate(NotImplemented("click misc. $index"), this)
    }

}