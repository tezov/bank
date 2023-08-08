

package com.tezov.bank.ui.pageSecondary.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.lib_adr_ui_core.theme.theme.colorsExtended

val PageWebViewTheme.colors: PageWebViewTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageWebViewTheme.provides(value: PageWebViewTheme.Colors) = localColors provides value

object PageWebViewTheme {

    data class Colors(
        val backgroundHeader: Color,
        val onBackgroundHeader: Color,
        val backgroundBody: Color,
        val accent: Color,
    )

    @Composable
    fun provideColors() = Colors(
        backgroundHeader = MaterialTheme.colorsExtended.background.default,
        onBackgroundHeader = MaterialTheme.colorsExtended.onBackground.default,
        backgroundBody = MaterialTheme.colorsExtended.background.shady,
        accent = MaterialTheme.colorsExtended.primary.accent,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }



}