/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import android.util.Log
import com.tezov.lib_core_android_kotlin.ui.component.chunk.TextField
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.PageState
import com.tezov.lib_core_kotlin.type.collection.ListEntry

class PageLoginAuthState private constructor(
    val textFieldValues: ListEntry<Field, TextField.State> = ListEntry(),
) : PageState {

    enum class Field{
        Login, Password
    }

    companion object {

        const val LOGIN_LENGTH = 4
        const val PASSWORD_LENGTH = 4

        fun create( ) = PageLoginAuthState( )
    }

    init {
        with(textFieldValues){
            add(Field.Login, TextField.State( "1234"))
            add(Field.Password, TextField.State( ""))
        }
    }

    val credentialValidState get() = textFieldValues.getValue(Field.Login)?.current?.length == LOGIN_LENGTH
            && textFieldValues.getValue(Field.Password)?.current?.length == PASSWORD_LENGTH
}