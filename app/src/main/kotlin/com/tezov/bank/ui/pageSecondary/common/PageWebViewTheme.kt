

package com.tezov.bank.ui.pageSecondary.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.colorsExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.typographiesExtended

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