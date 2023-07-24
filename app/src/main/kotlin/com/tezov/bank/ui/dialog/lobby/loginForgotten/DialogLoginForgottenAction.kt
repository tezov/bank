

package com.tezov.bank.ui.dialog.lobby.loginForgotten

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog.DialogAction
import com.tezov.lib_core_kotlin.async.notifier.Event

class DialogLoginForgottenAction private constructor(
    private val action: com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction,
    private val navigationController: NavigationController,
) : DialogAction<DialogLoginForgottenState> {

    companion object {
        @Composable
        fun create(
            action: com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.DialogAction,
            navigationController: NavigationController
        ) = DialogLoginForgottenAction(
            action = action,
            navigationController = navigationController,
        )
    }

    fun onClickCancel() {
        action.close()
    }

    fun onClickConfirm() {
        action.notify(Event.Confirm)
        action.close()
    }

}