/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class WindowInfo(
    val orientation: Int,
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeigh: Dp,
) {
    sealed class WindowType {
        object Compact : WindowType()
        object Medium : WindowType()
        object Extended : WindowType()
    }

    companion object {
        @Composable
        fun remember(): WindowInfo {
            val configuration = LocalConfiguration.current
            return WindowInfo(
                configuration.orientation,
                screenWidthInfo = when {
                    configuration.screenWidthDp < 600 -> WindowType.Compact
                    configuration.screenWidthDp < 840 -> WindowType.Medium
                    else -> WindowType.Extended
                },
                screenHeightInfo = when {
                    configuration.screenHeightDp < 480 -> WindowType.Compact
                    configuration.screenHeightDp < 840 -> WindowType.Medium
                    else -> WindowType.Extended
                },
                screenWidth = configuration.screenWidthDp.dp,
                screenHeigh = configuration.screenHeightDp.dp,
            )
        }
    }
}