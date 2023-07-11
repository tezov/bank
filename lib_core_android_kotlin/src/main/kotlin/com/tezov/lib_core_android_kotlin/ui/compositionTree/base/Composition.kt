/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.compositionTree.base

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver


interface Composition<S : CompositionState, A : CompositionAction<S>> {

    @Composable
    fun backPressListen() {
        val state = remember {
            mutableStateOf(false)
        }
        BackHandler(true) {
            state.value = true
        }
        if (!state.value) return
        state.value = false
        onBackPressedDispatch()
    }

    @Composable
    fun onBackPressedDispatch() {
    }

}