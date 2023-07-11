/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.dialog.auth.closeAppConfirmation

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.Finish
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog.DialogAction

class DialogCloseAppConfirmationAction private constructor(
    private val action: com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction,
    private val navigationController: NavigationController,
) : DialogAction<DialogCloseAppConfirmationState> {

    companion object {
        @Composable
        fun create(
            action: com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction,
            navigationController: NavigationController
        ) = DialogCloseAppConfirmationAction(
            action = action,
            navigationController = navigationController,
        )
    }

    fun onClickCancel() {
        action.close()
    }

    fun onClickConfirm() {
        navigationController.requestNavigate(Finish, this)
        action.close()
    }

}