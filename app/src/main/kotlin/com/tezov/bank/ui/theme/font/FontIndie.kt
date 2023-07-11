/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.theme.font

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.tezov.bank.R

val MaterialTheme.fontIndie: FontFamily
    @Composable
    @ReadOnlyComposable
    get() = FontRoboto.local.current.value

object Fontindie {
    val local = compositionLocalOf {
        lazy {
            FontFamily(
                Font(R.font.indie_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
            )
        }
    }
}


