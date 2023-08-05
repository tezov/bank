

package com.tezov.bank.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitPaletteColor
import com.tezov.lib_adr_sdk_core.ui.theme.theme.ThemeColorsExtended

val MaterialTheme.colorsPalette
    @Composable
    @ReadOnlyComposable
    get() = ThemeColorProviders.localPalette.current

object ThemeColorProviders {

    object Palette {

        val blackOverlay = Color(0xCA000000)
        val grayLightOverlay = Color(0x20B6B5B5)
        val blueDarkOverlay = Color(0x99022D52)

        val white = Color(0xFFFFFFFF)
        val whiteShiny = Color(0xFFEEEEEE)
        val whiteDark = Color(0xFFDDDDDD)
        val whiteShady = Color(0xFFEBE8E8)

        val grayBlack = Color(0xFF111010)
        val grayDark = Color(0xFF3F3F3F)
        val grayLight = Color(0xFF777777)

        val blueLight = Color(0xFFD8EDF1)
        val blueLightVariant = Color(0xFFDEEEF0)
        val blueShiny = Color(0xFFD0E9F1)
        val blueFade = Color(0xFFD8EDF1)
        val blueSea = Color(0xFF0CA9C2)
        val blueShadow = Color(0xFF364C53)
        val blueDark = Color(0xFF022330)
        val blueElegant = Color(0xFF12263F)
        val blueShady = Color(0xFF203550)

        val redBlood = Color(0xFFAD1720)

    }

    internal val localPalette = staticCompositionLocalOf { Palette }

    fun common() = ThemeColorsExtended.Common(
        background = OutfitPaletteColor(
            default = Palette.whiteShiny,
            accent = Palette.blueSea,
            overlay = Palette.blackOverlay,
            shady = Palette.whiteDark,
        ),
        onBackground = OutfitPaletteColor(
            default = Palette.blueElegant,
            accent = Palette.whiteShiny,
            overlay = Palette.whiteShiny,
            shady = Palette.blueElegant,
        ),
        primary = OutfitPaletteColor(
            default = Palette.blueDark,
            accent = Palette.blueSea,
            fade = Palette.grayLight,
            shiny = Palette.blueShiny,
            shady = Palette.blueShady,
            dark = Palette.blueShadow,
            light = Palette.grayDark,
        ),
        onPrimary = OutfitPaletteColor(
            default = Palette.white,
            accent = Palette.whiteShiny,
            fade = Palette.whiteShady,
            shiny = Palette.blueElegant,
        ),
        backgroundElevated = OutfitPaletteColor(
            default = Palette.blueLight,
            decor = Palette.blueDarkOverlay,
            overlay = Palette.grayLightOverlay,
            fade = Palette.blueFade,
            light = Palette.blueLightVariant,
            shady = Palette.whiteShady,
        ),
        onBackgroundElevated = OutfitPaletteColor(
            default = Palette.blueElegant,
        ),
        backgroundModal = OutfitPaletteColor(
            default = Palette.whiteShiny,
            accent = Palette.blueSea,
            fade = Palette.whiteDark,
        ),
        onBackgroundModal = OutfitPaletteColor(
            default = Palette.blueElegant,
        ),
    )


}