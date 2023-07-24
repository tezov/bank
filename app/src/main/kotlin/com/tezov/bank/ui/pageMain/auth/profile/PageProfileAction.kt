

package com.tezov.bank.ui.pageMain.auth.profile

import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmation
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.NotImplemented
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageAction

class PageProfileAction private constructor(
    private val navigationController: NavigationController,
    private val dialogAction: DialogAction,
) : PageAction<PageProfileState> {

    companion object {

        fun create(
            navigationController: NavigationController,
            dialogAction: DialogAction
        ) = PageProfileAction(
            navigationController = navigationController,
            dialogAction = dialogAction
        )
    }


    fun onClickExit() {
        dialogAction.showOnCardWithOverlay(this) {
            DialogCloseAppConfirmation()
        }
    }

    fun onClickProfiles(index: Int) {
        navigationController.requestNavigate(NotImplemented("click profile $index"), this)
    }

    fun onClickDocuments(index: Int) {
        navigationController.requestNavigate(NotImplemented("click document $index"), this)
    }

    fun onClickOffers(index: Int) {
        navigationController.requestNavigate(NotImplemented("click offer $index"), this)
    }

    fun onClickHelps(index: Int) {
        navigationController.requestNavigate(NotImplemented("click action $index"), this)
    }

}