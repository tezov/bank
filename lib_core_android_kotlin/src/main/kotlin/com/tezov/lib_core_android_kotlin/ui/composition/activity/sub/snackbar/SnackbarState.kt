/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySubState

class SnackbarState private constructor(
    val hostState: SnackbarHostState
) : ActivitySubState {
    companion object {
        @Composable
        fun create(
            snackbarHostState: SnackbarHostState,
        ) = SnackbarState(
            hostState = snackbarHostState
        )
    }
}