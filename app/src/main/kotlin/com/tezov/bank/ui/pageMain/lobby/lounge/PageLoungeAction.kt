/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageMain.lobby.lounge

import com.tezov.bank.navigation.NavigationRoutes.Route
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.NotImplemented
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageAction

class PageLoungeAction private constructor(
    private val navigationController: NavigationController,
) : PageAction<PageLoungeState> {

    companion object {

        fun create(
            navigationController: NavigationController,
        ) = PageLoungeAction(
            navigationController = navigationController,
        )
    }

    fun onClickAdd() {
        navigationController.requestNavigate(NotImplemented("click add"), this)
    }

    fun onClickMenu(index:Int) {
        navigationController.requestNavigate(NotImplemented("click menu $index"), this)
    }

    fun onClickBalanceActivate() {
        navigationController.requestNavigate(NotImplemented("click balance activate"), this)
    }

    fun onClickConnect() {
        navigationController.requestNavigate(Route.LoginAuth, this)
    }

    fun onClickSendMoney() {
        navigationController.requestNavigate(NotImplemented("click send money"), this)
    }

    fun onClickHelpAndService() {
        navigationController.requestNavigate(Route.HelpAndService, this)
    }

}