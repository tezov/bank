/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.dialog.auth.closeAppConfirmation

import androidx.compose.runtime.Composable
import com.tezov.bank.navigation.NavigationRoutes.Route
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalPagesBundle

class DialogCloseAppController private constructor(
    private val navigationController: NavigationController,
    private val dialogAction: DialogAction,
) {

    companion object {
        @Composable
        fun create(
            navigationController: NavigationController,
            dialogAction: DialogAction
        ) = DialogCloseAppController(
            navigationController = navigationController,
            dialogAction = dialogAction
        )

        @Composable
        fun handleOnBackPressed(): Boolean {
            DiAccessorAppUiPage()
                .with(key = LocalPagesBundle.last().current)
                .controllerDialogAuthCloseApp().apply {
                    //TODO fix that shit, should not have to be remembered each time we check if we wanna manage the back press event ...
                    //quick fix, never dispose it
                    remember(false)
                }.get().takeIf {
                    it.navigationController.isLastRoute || it.navigationController.currentRoute()?.path == Route.Account.path
                }?.let {
                    it.show(this)
                    return true
                }
            return false
        }

    }

    fun show(owner:Any) {
        dialogAction.showOnCardWithOverlay(owner) {
            DialogCloseAppConfirmation()
        }
    }

}