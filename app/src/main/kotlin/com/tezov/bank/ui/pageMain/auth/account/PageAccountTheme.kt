

package com.tezov.bank.ui.pageMain.auth.account

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_adr_ui_cpt.component.animated.block.SummaryCardAnimated
import com.tezov.lib_adr_ui_cpt.component.animated.block.SummaryCardAnimated.Style.Companion.copy
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.lib_adr_ui_cpt.component.core.chunk.DropDownMenu.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Icon
import com.tezov.lib_adr_ui_cpt.component.core.layout.ColumnCollapsibleHeader
import com.tezov.lib_adr_ui_core.theme.style.OutfitFrameStateColor
import com.tezov.lib_adr_ui_core.theme.style.OutfitShape.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_ui_core.theme.theme.*
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleValueRow
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleValueRow.Style.Companion.copy

val PageAccountTheme.colors: PageAccountTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageAccountTheme.provides(value: PageAccountTheme.Colors) = localColors provides value

val PageAccountTheme.dimensions: PageAccountTheme.Dimensions
    @Composable
    @ReadOnlyComposable
    get() = localDimensions.current

infix fun PageAccountTheme.provides(value: PageAccountTheme.Dimensions) =
    localDimensions provides value

val PageAccountTheme.typographies: PageAccountTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun PageAccountTheme.provides(value: PageAccountTheme.Typographies) =
    localTypographies provides value

val PageAccountTheme.styles: PageAccountTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageAccountTheme.provides(value: PageAccountTheme.Style) = localStyles provides value


object PageAccountTheme {

    data class Colors(
        val background: Color,
        val backgroundElevated: Color,
        val backgroundElevatedOverlay: Color,
        val accent: Color,
        val primary: Color,
        val fade: Color,
    )

    @Composable
    fun provideColors() = Colors(
        background = MaterialTheme.colorsExtended.background.default,
        backgroundElevated = MaterialTheme.colorsExtended.backgroundElevated.default,
        backgroundElevatedOverlay = MaterialTheme.colorsExtended.backgroundElevated.overlay,
        accent = MaterialTheme.colorsExtended.primary.accent,
        primary = MaterialTheme.colorsExtended.primary.default,
        fade = MaterialTheme.colorsExtended.primary.fade,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Dimensions(
        val spacingBottomHeaderBackground: Dp,
        val headerProperties: ColumnCollapsibleHeader.Properties,
    )

    @Composable
    fun provideDimensions() = Dimensions(
        spacingBottomHeaderBackground = 56.dp,
        headerProperties = ColumnCollapsibleHeader.Properties(
            min = (MaterialTheme.dimensionsPaddingExtended.element.normal.vertical * 2) + 80.dp,
            max = (MaterialTheme.dimensionsPaddingExtended.element.normal.vertical * 2) + 184.dp
        )
    )

    internal val localDimensions: ProvidableCompositionLocal<Dimensions> =
        staticCompositionLocalOf {
            error("not provided")
        }


    data class Typographies(
        val headline: OutfitTextStateColor,
    )

    @Composable
    fun provideTypographies() = Typographies(
        headline = MaterialTheme.typographiesExtended.title.supra.copy {
            outfitState = colors.primary.asStateSimple
        },
    )

    internal val localTypographies: ProvidableCompositionLocal<Typographies> =
        staticCompositionLocalOf {
            error("not provided")
        }

    data class Style(
        val icon: Icon.StateColor.Style,
        val accountSummary: SummaryCardAnimated.Style,
        val sectionAccountValue: SectionSimpleValueRow.Style,
    )

    @Composable
    fun provideStyles() = Style(
        icon = Icon.StateColor.Style(
            size = MaterialTheme.dimensionsIconExtended.action.big,
            tint = colors.background,
            outfitFrame = OutfitFrameStateColor(
                outfitShape = MaterialTheme.shapesExtended.icon.normal.copy {
                    outfitState = colors.primary.asStateSimple
                }
            )
        ),
        accountSummary = ThemeComponentProviders.accountSummaryCard().copy {
            styleDropDownMenu = styleDropDownMenu.copy {
                styleIcon = Icon.StateColor.Style(
                    size = MaterialTheme.dimensionsIconExtended.action.normal,
                    tint = MaterialTheme.colorsExtended.onPrimary.default,
                )
            }
        },
        sectionAccountValue = ThemeComponentProviders.sectionAccountValueSimpleRowStyle().copy {
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
        }

    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}