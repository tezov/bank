

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