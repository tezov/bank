/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog

import androidx.compose.runtime.*
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.Modal
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.Modal.Companion.LocalModal
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page.Companion.LocalModalsBundle

interface Dialog<S : DialogState, A : DialogAction<S>> : Modal<S, A> {
    companion object {
        val LocalDialog @Composable get() = LocalModal.current as Dialog<*,*>
    }

    @Composable
    override fun Modal<S, A>.content() {
        (this as Dialog<S, A>).content()
    }

    @Composable
    fun Dialog<S, A>.content()

}