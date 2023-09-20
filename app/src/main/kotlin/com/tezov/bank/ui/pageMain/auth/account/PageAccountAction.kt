

package com.tezov.bank.ui.pageMain.auth.account

import com.tezov.bank.navigation.NavigationRouteManager.Route
import com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncoming
import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.Route.NotImplemented
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.bottomsheet.BottomSheetAction
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageAction

class PageAccountAction private constructor(
    private val navigationController: NavigationController,
    private val bottomsheetAction: BottomSheetAction,
) :
    PageAction<PageAccountState>() {

    companion object {

        fun create(
            navigationController: NavigationController,
            bottomsheetAction: BottomSheetAction
        ) = PageAccountAction(
            navigationController = navigationController,
            bottomsheetAction = bottomsheetAction,
        )
    }

    fun onClickIncomingHelp() {
        bottomsheetAction.showOnSheetWithOverlay(this) {
            BottomSheetAccountIncoming()
        }
    }

    fun onClickAccountSummary(index: Int) {
        navigationController.requestNavigate(NotImplemented("click menu $index"), this)
    }

    fun onClickAccountHistories(section: Int, index: Int) {
        navigationController.requestNavigate(NotImplemented("click history $section:$index"), this)
    }

    fun onClickMessageInfo() {
        navigationController.requestNavigate(Route.MessageInfo, this)
    }

    fun onClickAccount() {
        navigationController.requestNavigate(NotImplemented("click account"), this)
    }

}