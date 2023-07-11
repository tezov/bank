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

val MaterialTheme.fontRoboto: FontFamily
    @Composable
    @ReadOnlyComposable
    get() = FontRoboto.local.current.value

object FontRoboto {
    val local = compositionLocalOf {
        lazy {
            FontFamily(
                Font(R.font.roboto_thin, weight = FontWeight.Light, style = FontStyle.Normal),
                Font(
                    R.font.roboto_thin_italic,
                    weight = FontWeight.Light,
                    style = FontStyle.Italic
                ),
                Font(R.font.roboto_light, weight = FontWeight.Normal, style = FontStyle.Normal),
                Font(
                    R.font.roboto_light_italic,
                    weight = FontWeight.Normal,
                    style = FontStyle.Italic
                ),
                Font(R.font.roboto_regular, weight = FontWeight.SemiBold, style = FontStyle.Normal),
                Font(R.font.roboto_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
                Font(R.font.roboto_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
                Font(R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
            )
        }
    }
}


