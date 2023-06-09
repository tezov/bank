/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageMain.auth.payment

import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.NotImplemented
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageAction

class PagePaymentAction private constructor(
    private val navigationController: NavigationController,
) :
    PageAction<PagePaymentState> {


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