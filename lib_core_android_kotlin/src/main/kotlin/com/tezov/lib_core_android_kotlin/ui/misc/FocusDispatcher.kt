/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.misc

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.tezov.lib_core_kotlin.extension.ExtensionBoolean.isTrue
import com.tezov.lib_core_kotlin.type.collection.ListEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
class FocusDispatcher {

    inner class FocusId internal constructor(internal val autoShowKeyboard:Boolean){

        internal val value: FocusRequester = FocusRequester()

        internal fun onFocus() { onFocus(this) }

        fun hasFocus() = hasFocus(this)

        fun requestFocus() = requestFocus(this)

    }

    private val ids = mutableListOf<FocusId>()
    private var keyboardController: SoftwareKeyboardController? = null
    private lateinit var coroutine: CoroutineScope
    private lateinit var focusManager: FocusManager
    private lateinit var focusOwner: MutableState<FocusId?>

    fun createId(autoShowKeyboard:Boolean = true) = FocusId(autoShowKeyboard).also { ids.add(it) }

    fun destroyId(id: FocusId) = ids.remove(id)

    @Composable
    fun compose() {
        coroutine = rememberCoroutineScope()
        keyboardController = LocalSoftwareKeyboardController.current
        focusManager = LocalFocusManager.current
        focusOwner = remember {
            mutableStateOf(null)
        }
    }

    fun showKeyboard() {
        coroutine.launch {
            delay(150)
            keyboardController?.show()
        }
    }

    fun hideKeyboard() {
        keyboardController?.hide()
    }

    fun requestClearFocus() {
        focusOwner.value = null
        focusManager.clearFocus(true)
        hideKeyboard()
    }

    private fun onFocus(id: FocusId){
        focusOwner.value = id
        if(id.autoShowKeyboard){
            showKeyboard()
        }
        else{
            hideKeyboard()
        }
    }

    fun requestFocus(id: FocusId){
        if (focusOwner.value != id) {
            kotlin.runCatching {
                //TODO no idea why but throw "IllegalStateException" but the focus is obtained anyway ...
                //maybe add focus cemetery same i did in java?
                id.value.requestFocus()
            }
        }
    }

    fun hasFocus(id: FocusId) = focusOwner.value == id

}