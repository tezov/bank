

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


