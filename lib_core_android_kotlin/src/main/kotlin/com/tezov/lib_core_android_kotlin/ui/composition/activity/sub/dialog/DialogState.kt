/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalLevel
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalPagesBundle
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySubState
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page.Companion.LocalPageBundle

class DialogState private constructor(
    val stateUpdated: MutableState<Int>,
) : ActivitySubState {

    companion object {
        @Composable
        fun create(
            dialogContentUpdated: MutableState<Int> = mutableStateOf(0)
        ) = DialogState(
            stateUpdated = dialogContentUpdated,
        )
    }

    var isVisible = false
        get() = (stateUpdated.value > 0) && field
        private set

    private var _content: (@Composable () -> Unit) = { }

    @Composable
    internal fun content() = _content()

    internal fun show(content: (@Composable () -> Unit)?) {
        if (content == null) {
            _content = { }
            isVisible = false
        } else {
            _content = {
                CompositionLocalProvider(
                    LocalLevel provides 1,
                    LocalPageBundle provides LocalPagesBundle.last(),
                ) {
                    content()
                }
            }
            isVisible = true
        }
        stateUpdated.value++
    }

}