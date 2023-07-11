/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySubState
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page

@OptIn(ExperimentalMaterialApi::class)
class BottomSheetState private constructor(
    val bottomSheetState: ModalBottomSheetState,
    private val stateUpdated: MutableState<Int>
) : ActivitySubState {

    companion object {
        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun create(
            bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
            sheetContentUpdated: MutableState<Int> = mutableStateOf(0)
        ) = BottomSheetState(
            bottomSheetState = bottomSheetState,
            stateUpdated = sheetContentUpdated,
        )
    }

    internal var _isVisible = false

    val isVisible get() = (stateUpdated.value > 0) && _isVisible

    private var _content: (@Composable () -> Unit) = { }

    @Composable
    internal fun content() = _content()

    internal fun show(content: (@Composable () -> Unit)?) {
        if (content == null) {
            _content = { }
            _isVisible = false
        } else {
            _content = { Page.CompositionLocalProvider { content() } }
            _isVisible = true
        }
        stateUpdated.value++
    }

}