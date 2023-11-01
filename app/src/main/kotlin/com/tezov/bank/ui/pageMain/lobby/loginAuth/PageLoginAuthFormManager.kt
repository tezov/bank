

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState.Field
import com.tezov.lib_adr_ui_core.misc.FocusDispatcher
import com.tezov.lib_adr_ui_cpt.component.core.chunk.TextField
import com.tezov.lib_adr_core.type.collection.ListEntry

//TODO modifier lenght control par une validation login / password
//doit etre fait a travers le TextField.State par un Validator (cf. reprendre le travail en Java)

@Composable
fun rememberPageLoginAuthFormManager(textFieldValues: ListEntry<Field, TextField.State>):PageLoginAuthFormManager {
    return remember {
        PageLoginAuthFormManager(textFieldValues = textFieldValues)
    }.also { it.compose() }
}

class PageLoginAuthFormManager internal constructor(
    private var focusDispatcher: FocusDispatcher = FocusDispatcher(),
    private val textFieldValues: ListEntry<Field, TextField.State>,
) {
    init {
        textFieldValues.forEach {
            it.value.focusId = focusDispatcher.createId()
        }
    }

    @Composable
    internal fun compose() = focusDispatcher.compose()

    fun hideKeyboard() = focusDispatcher.hideKeyboard()

    fun requestClearFocus() = focusDispatcher.requestClearFocus()

    fun requestFocus(state: TextField.State) = state.focusId?.let {
        focusDispatcher.requestFocus(it)
    }

    fun hasFocus(state: TextField.State) = state.focusId?.let {
        focusDispatcher.hasFocus(it)
    } ?: false

    fun onChange(field: Field) {
        when (field) {
            Field.Login -> onLoginChange()
            Field.Password -> onPasswordChange()
        }
    }

    private fun onLoginChange() {
        val login = textFieldValues.getValue(Field.Login)
        val password = textFieldValues.getValue(Field.Password)
        if (login == null || password == null) {
            throw IllegalStateException("can't be null")
        }
        if (login.current.length >= PageLoginAuthState.LOGIN_LENGTH) {
            if (password.current.length < PageLoginAuthState.PASSWORD_LENGTH) {
                requestFocus(password)
            } else {
                requestClearFocus()
            }
        }
    }

    private fun onPasswordChange() {
        val login = textFieldValues.getValue(Field.Login)
        val password = textFieldValues.getValue(Field.Password)
        if (login == null || password == null) {
            throw IllegalStateException("can't be null")
        }
        if (password.current.length < PageLoginAuthState.PASSWORD_LENGTH) {
            requestFocus(password)
        } else if (login.current.length < PageLoginAuthState.LOGIN_LENGTH) {
            requestFocus(login)
        } else {
            requestClearFocus()
        }
    }

}