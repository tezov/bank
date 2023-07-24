

package com.tezov.bank.ui.pageMain.auth.discover

import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.NotImplemented
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageAction

class PageDiscoverAction private constructor(
    private val navigationController: NavigationController,
) :
    PageAction<PageDiscoverState> {


    companion object {

        fun create(
            navigationController: NavigationController
        ) = PageDiscoverAction(
            navigationController = navigationController,
        )
    }

    fun onClickCardsWithButton(index: Int) {
        navigationController.requestNavigate(NotImplemented("click card button $index"), this)
    }

    fun onClickCardsWithLink(index: Int) {
        navigationController.requestNavigate(NotImplemented("click card link $index"), this)
    }

    fun onClickCashbacksCard(index: Int) {
        navigationController.requestNavigate(NotImplemented("click cashback $index"), this)
    }

    fun onClickCashbacksButton() {
        navigationController.requestNavigate(NotImplemented("click cashback button"), this)
    }

    fun onClickOffers(index: Int) {
        navigationController.requestNavigate(NotImplemented("click offer $index"), this)
    }

    fun onClickTips(index: Int) {
        navigationController.requestNavigate(NotImplemented("click tip $index"), this)
    }


}