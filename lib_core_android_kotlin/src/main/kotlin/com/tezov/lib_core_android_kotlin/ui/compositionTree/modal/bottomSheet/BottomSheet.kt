/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.bottomSheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.Modal
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.Modal.Companion.LocalModal
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.dialog.Dialog
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page.Companion.LocalModalsBundle

interface BottomSheet<S : BottomSheetState, A : BottomSheetAction<S>> : Modal<S, A> {
    companion object {
        val LocalBottomSheet @Composable get() = LocalModal.current as BottomSheet<*, *>
    }

    @Composable
    override fun Modal<S, A>.content() {
        (this as BottomSheet<S, A>).content()
    }

    @Composable
    fun BottomSheet<S, A>.content()

}