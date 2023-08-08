

package com.tezov.bank.ui.pageMain.lobby.help_and_service

import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.NotImplemented
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageAction

class PageHelpAndServiceAction private constructor(
    private val navigationController: NavigationController,
) : PageAction<PageHelpAndServiceState> {

    companion object {

        fun create(
            navigationController: NavigationController,
        ) = PageHelpAndServiceAction(
            navigationController = navigationController

        )
    }

    fun onClickClose() {
        navigationController.requestNavigateBack(this)
    }

    fun onClickHelpAndServices(index: Int) {
        navigationController.requestNavigate(NotImplemented("click help $index"), this)
    }

    fun onClickContacts(index: Int) {
        navigationController.requestNavigate(NotImplemented("click contact $index"), this)
    }

    fun onClickNotices(index: Int) {
        navigationController.requestNavigate(NotImplemented("click notices $index"), this)
    }

}