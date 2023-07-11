/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageSecondary.common

import android.util.Log
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageAction

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