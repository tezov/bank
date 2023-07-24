

package com.tezov.bank.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.tezov.bank.ui.theme.font.fontIbm
import com.tezov.bank.ui.theme.font.fontIndie
import com.tezov.bank.ui.theme.font.fontRoboto
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitPaletteSize
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitText.StateColor.Style.Companion.asPaletteSize
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitText.StateColor.Style.Companion.asTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeTypographiesExtended

object ThemeTypographyProviders {

    @Composable
    fun common() = ThemeTypographiesExtended.Common(
        title = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ).asTextStateColor,
            big = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ).asTextStateColor,
            huge = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            ).asTextStateColor,
            supra = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ).asTextStateColor
        ),
        body = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontIbm,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ).asTextStateColor,
            small = TextStyle(
                fontFamily = MaterialTheme.fontIbm,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ).asTextStateColor
        ),
        subtitle = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontIbm,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            ).asTextStateColor
        ),
        helper = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontIbm,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            ).asTextStateColor,
            big = TextStyle(
                fontFamily = MaterialTheme.fontIbm,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ).asTextStateColor,
        ),
        button = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontIbm,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                letterSpacing = 1.1.sp
            ).asTextStateColor,
            small = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp,
                fontSize = 12.sp
            ).asTextStateColor,
            big = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            ).asTextStateColor,
        ),
        link = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline
            ).asTextStateColor,
            big = TextStyle(
                fontFamily = MaterialTheme.fontIndie,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            ).asTextStateColor,
        ),
        input = TextStyle(
            fontFamily = MaterialTheme.fontIbm,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ).asTextStateColor.asPaletteSize,
        label = OutfitPaletteSize(
            normal = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            ).asTextStateColor,
            big = TextStyle(
                fontFamily = MaterialTheme.fontRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            ).asTextStateColor
        ),
        caption = TextStyle(
            fontFamily = MaterialTheme.fontIbm,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ).asTextStateColor.asPaletteSize,
        menu = TextStyle(
            fontFamily = MaterialTheme.fontRoboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ).asTextStateColor.asPaletteSize,
    )

}