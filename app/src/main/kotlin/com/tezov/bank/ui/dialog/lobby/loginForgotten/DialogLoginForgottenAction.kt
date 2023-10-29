

package com.tezov.bank.ui.dialog.lobby.loginForgotten

import androidx.compose.runtime.Composable
import com.tezov.lib_adr_app_core.navigation.NavigationController
import com.tezov.lib_adr_app_core.ui.compositionTree.modal.dialog.DialogAction
import com.tezov.lib_adr_core.async.notifier.Event

class DialogLoginForgottenAction private constructor(
    private val action: com.tezov.lib_adr_app_core.ui.composition.activity.sub.dialog.DialogAction,
    private val navigationController: NavigationController,
) : DialogAction<DialogLoginForgottenState>() {

    companion object {
        @Composable
        fun create(
            action: com.tezov.lib_adr_app_core.ui.composition.activity.sub.dialog.DialogAction,
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
        action.notifier.tryEmit(Event.Confirm)
        action.close()
    }

}