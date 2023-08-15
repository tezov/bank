

package com.tezov.bank.ui.pageMain.auth.discover

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleTile
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleTile.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleRow
import com.tezov.lib_adr_ui_cpt.component.menu.block.SectionSimpleRow.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.slide.block.SectionCarouselCard
import com.tezov.lib_adr_ui_cpt.component.slide.block.SectionCarouselCard.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.slide.block.SectionRollerCard
import com.tezov.lib_adr_ui_cpt.component.slide.block.SectionRollerCard.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.menu.element.SimpleTile.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.slide.element.RollerCard.Style.Companion.copy
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.lib_adr_ui_core.type.primaire.DpSize
import com.tezov.lib_adr_ui_core.type.primaire.dpSize
import com.tezov.lib_adr_ui_cpt.component.core.block.HorizontalPager.WithIndicator.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.core.block.HorizontalRoller.Simple.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Icon.Simple.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Image.Simple.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_ui_core.theme.theme.colorsExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsIconExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_ui_core.theme.theme.typographiesExtended

val PageDiscoverTheme.colors: PageDiscoverTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageDiscoverTheme.provides(value: PageDiscoverTheme.Colors) = localColors provides value

val PageDiscoverTheme.typographies: PageDiscoverTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun PageDiscoverTheme.provides(value: PageDiscoverTheme.Typographies) =
    localTypographies provides value

val PageDiscoverTheme.dimensions: PageDiscoverTheme.Dimensions
    @Composable
    @ReadOnlyComposable
    get() = localDimensions.current

infix fun PageDiscoverTheme.provides(value: PageDiscoverTheme.Dimensions) =
    localDimensions provides value

val PageDiscoverTheme.styles: PageDiscoverTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageDiscoverTheme.provides(value: PageDiscoverTheme.Style) = localStyles provides value

object PageDiscoverTheme {

    data class Colors(
        val background: Color,
        val backgroundElevated: Color,
        val backgroundElevatedOverlay: Color,
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
        backgroundElevatedOverlay = MaterialTheme.colorsExtended.backgroundElevated.overlay,
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

    data class Dimensions(
        val header: Dp,
        val iconCard: DpSize,
        val imageCard: DpSize,
        val spacingTopSectionRowToBottomSectionCard: Dp,
    )

    @Composable
    fun provideDimensions() = Dimensions(
        header = 192.dp,
        iconCard = 64.dpSize,
        imageCard = 88.dpSize,
        spacingTopSectionRowToBottomSectionCard = 64.dp,
    )

    internal val localDimensions: ProvidableCompositionLocal<Dimensions> =
        staticCompositionLocalOf {
            error("not provided")
        }

    data class Style(
        val sectionCarouselCardButton: SectionCarouselCard.Style,
        val sectionCarouselCardLink: SectionCarouselCard.Style,
        val sectionRollerCard: SectionRollerCard.Style,
        val sectionActionCard: SectionSimpleTile.Style,
        val sectionActionRow: SectionSimpleRow.Style,
    )

    @Composable
    fun provideStyles() = Style(
        sectionCarouselCardButton = ThemeComponentProviders.sectionCarouselCardStyle(
            cardStyle = ThemeComponentProviders.carouselCardButtonStyle()
        ).copy{
            styleCarousel = styleCarousel.copy {
                paddingTopIndicator = MaterialTheme.dimensionsPaddingExtended.element.huge.vertical
                heightItemToHighest = true
            }
        },
        sectionCarouselCardLink = ThemeComponentProviders.sectionCarouselCardStyle(
            cardStyle = ThemeComponentProviders.carouselCardLinkStyle()
        ).copy{
            styleCarousel = styleCarousel.copy {
                paddingTopIndicator = MaterialTheme.dimensionsPaddingExtended.element.huge.vertical
                heightItemToHighest = true
            }
        },
        sectionRollerCard = ThemeComponentProviders.sectionRollerCardStyle().copy{
            styleRoller = styleRoller.copy {
                heightItemToHighest = true
            }
            styleCard = styleCard.copy{
                styleImage = styleImage.copy{
                    size = dimensions.imageCard
                }
            }
        },
        sectionActionCard = ThemeComponentProviders.sectionTileFillStyle().copy {
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
            styleTile = styleTile.copy {
                styleIconInfo = styleIconInfo.copy {
                    size = MaterialTheme.dimensionsIconExtended.info.huge
                }
            }
        },
        sectionActionRow = ThemeComponentProviders.sectionSimpleRowStyle().copy {
            colorBackgroundHeader = null
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
            outfitTextTitle = MaterialTheme.typographiesExtended.title.huge.copy {
                typo = typo.copy(
                    fontWeight = FontWeight.Bold
                )
            }
        },
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}