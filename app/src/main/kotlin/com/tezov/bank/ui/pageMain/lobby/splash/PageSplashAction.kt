

package com.tezov.bank.ui.pageMain.lobby.splash

import com.tezov.bank.navigation.NavigationRoutes.Route
import com.tezov.lib_adr_sdk_core.navigation.NavigationController
import com.tezov.lib_adr_sdk_core.ui.compositionTree.page.PageAction

class PageSplashAction private constructor(
    private val navigationController: NavigationController,
) :
    PageAction<PageSplashState> {


    companion object {

        fun create(
            navigationController: NavigationController
        ) = PageSplashAction(
            navigationController = navigationController,
        )
    }

    fun onStart() {
        navigationController.requestNavigate(Route.Lounge, this)
    }

}