

package com.tezov.bank.ui.pageMain.lobby.help_and_service

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleTile
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleTile.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleRow
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleRow.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.menu.element.SimpleTile.Style.Companion.copy
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Icon.Simple.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_ui_core.theme.theme.colorsExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsIconExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_ui_core.theme.theme.typographiesExtended

val PageHelpAndServiceTheme.colors: PageHelpAndServiceTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageHelpAndServiceTheme.provides(value: PageHelpAndServiceTheme.Colors) =
    localColors provides value

val PageHelpAndServiceTheme.typographies: PageHelpAndServiceTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun PageHelpAndServiceTheme.provides(value: PageHelpAndServiceTheme.Typographies) =
    localTypographies provides value

val PageHelpAndServiceTheme.styles: PageHelpAndServiceTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageHelpAndServiceTheme.provides(value: PageHelpAndServiceTheme.Style) =
    localStyles provides value

object PageHelpAndServiceTheme {

    data class Colors(
        val background: Color,
        val backgroundElevated: Color,
        val accent: Color,
        val primary: Color,
        val neutral: Color,
        val decor: Color,
        val fade: Color,
    )

    @Composable
    fun provideColors() = Colors(
        background = MaterialTheme.colorsExtended.background.default,
        backgroundElevated = MaterialTheme.colorsExtended.backgroundElevated.default,
        accent = MaterialTheme.colorsExtended.primary.accent,
        primary = MaterialTheme.colorsExtended.primary.default,
        neutral = MaterialTheme.colorsExtended.primary.shady,
        decor = MaterialTheme.colorsExtended.backgroundElevated.decor,
        fade = MaterialTheme.colorsExtended.primary.fade,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Typographies(
        val titleHuge: OutfitTextStateColor,
    )

    @Composable
    fun provideTypographies() = Typographies(
        titleHuge = MaterialTheme.typographiesExtended.title.huge.copy {
            outfitState = colors.primary.asStateSimple
        },
    )

    internal val localTypographies: ProvidableCompositionLocal<Typographies> =
        staticCompositionLocalOf {
            error("not provided")
        }


    data class Style(
        val sectionRow: SectionSimpleRow.Style,
        val sectionCard: SectionSimpleTile.Style,
    )

    @Composable
    fun provideStyles() = Style(
        sectionRow = ThemeComponentProviders.sectionSimpleRowStyle().copy {
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
        },
        sectionCard = ThemeComponentProviders.sectionTileOutlinedStyle().copy {
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
            styleTile = styleTile.copy {
                styleIconInfo = styleIconInfo.copy {
                    size = MaterialTheme.dimensionsIconExtended.info.big
                }
            }
        },
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}