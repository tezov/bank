

package com.tezov.bank.ui.pageSecondary.auth.messageInfo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.bank.ui.component.block.SectionMessageRow
import com.tezov.bank.ui.component.block.SectionMessageRow.Style.Companion.copy
import com.tezov.bank.ui.component.block.SectionSimpleRow
import com.tezov.bank.ui.component.block.SectionSimpleRow.Style.Companion.copy
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.lib_core_android_kotlin.ui.type.primaire.DpSize
import com.tezov.lib_core_android_kotlin.ui.type.primaire.dpSize
import com.tezov.lib_core_android_kotlin.ui.component.block.HorizontalPager
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Icon
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Image
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitBorder.StateColor.Style.Companion.copy
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitBorderStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitShape.StateColor.Style.Companion.asFrameStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitShape.StateColor.Style.Companion.copy
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitShapeStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.*

val PageMessageInfoTheme.colors: PageMessageInfoTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageMessageInfoTheme.provides(value: PageMessageInfoTheme.Colors) = localColors provides value

val PageMessageInfoTheme.styles: PageMessageInfoTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageMessageInfoTheme.provides(value: PageMessageInfoTheme.Style) = localStyles provides value

object PageMessageInfoTheme {

    data class Colors(
        val background: Color,
    )

    @Composable
    fun provideColors() = Colors(
        background = MaterialTheme.colorsExtended.background.default,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Style(
        val pagerTabRow: HorizontalPager.WithTabRow.Style,
        val sectionRow: SectionMessageRow.Style,
    )

    @Composable
    fun provideStyles() = Style(
        pagerTabRow = ThemeComponentProviders.pagerWidthTabRowStyle(),
        sectionRow = ThemeComponentProviders.sectionMessageRowStyle().copy{
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
        },
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}