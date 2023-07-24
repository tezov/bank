

package com.tezov.bank.ui.dialog.lobby.loginForgotten

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog.DialogState

class DialogLoginForgottenState private constructor( ) : DialogState {
    var contentData: ContentData

    companion object {

        @Composable
        fun create() = DialogLoginForgottenState()
    }

    data class ContentData(
        val title: String,
        val body: String,
        val cancel: String,
        val confirm: String,
    )

    init {
        contentData = ContentData(
            title = "Mon numéro client ?",
            body = "Votre numéro client vous a été envoyé par courrier quelques jours aprés l'ouverture de votre compte. Si toutefois vous le souhaitez, vous pouvez le récupérer de façon sécurisée dés maintenant.",
            confirm = "RÉCUPÉRER MON NUMÉRO CLIENT",
            cancel = "FERMER",
        )
    }

}