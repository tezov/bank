

package com.tezov.bank.ui.pageMain.auth.profile

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.bank.ui.component.block.SectionSimpleRow
import com.tezov.bank.ui.component.block.SectionSimpleRow.Style.Companion.copy
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.lib_adr_sdk_core.ui.type.primaire.DpSize
import com.tezov.lib_adr_sdk_core.ui.type.primaire.dpSize
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Image
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitBorder.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitBorderStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitShape.StateColor.Style.Companion.asFrameStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitShape.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitShapeStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.theme.*

val PageProfileTheme.colors: PageProfileTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageProfileTheme.provides(value: PageProfileTheme.Colors) = localColors provides value

val PageProfileTheme.dimensions: PageProfileTheme.Dimensions
    @Composable
    @ReadOnlyComposable
    get() = localDimensions.current

infix fun PageProfileTheme.provides(value: PageProfileTheme.Dimensions) =
    localDimensions provides value

val PageProfileTheme.shapes: PageProfileTheme.Shapes
    @Composable
    @ReadOnlyComposable
    get() = localShapes.current

infix fun PageProfileTheme.provides(value: PageProfileTheme.Shapes) = localShapes provides value


val PageProfileTheme.borders: PageProfileTheme.Borders
    @Composable
    @ReadOnlyComposable
    get() = localBorders.current

infix fun PageProfileTheme.provides(value: PageProfileTheme.Borders) = localBorders provides value

val PageProfileTheme.typographies: PageProfileTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun PageProfileTheme.provides(value: PageProfileTheme.Typographies) =
    localTypographies provides value

val PageProfileTheme.styles: PageProfileTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageProfileTheme.provides(value: PageProfileTheme.Style) = localStyles provides value

object PageProfileTheme {

    data class Colors(
        val background: Color,
        val backgroundElevated: Color,
        val accent: Color,
        val primary: Color,
        val neutral: Color,
        val decor: Color,
        val fade: Color,
        val dark: Color,
    )

    @Composable
    fun provideColors() = Colors(
        background = MaterialTheme.colorsExtended.background.default,
        backgroundElevated = MaterialTheme.colorsExtended.backgroundElevated.overlay,
        accent = MaterialTheme.colorsExtended.primary.accent,
        primary = MaterialTheme.colorsExtended.primary.default,
        neutral = MaterialTheme.colorsExtended.primary.shady,
        decor = MaterialTheme.colorsExtended.primary.shiny.copy(alpha = 0.65f),
        fade = MaterialTheme.colorsExtended.primary.fade,
        dark = MaterialTheme.colorsExtended.primary.dark,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Dimensions(
        val iconUser: DpSize,
    )

    @Composable
    fun provideDimensions() = Dimensions(
        iconUser = 84.dpSize,
    )

    internal val localDimensions: ProvidableCompositionLocal<Dimensions> =
        staticCompositionLocalOf {
            error("not provided")
        }

    data class Shapes(
        val icon: OutfitShapeStateColor,
    )

    @Composable
    fun provideShapes() = Shapes(
        icon = MaterialTheme.shapesExtended.icon.normal
    )

    internal val localShapes: ProvidableCompositionLocal<Shapes> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Borders(
        val icon: OutfitBorderStateColor,
    )

    @Composable
    fun provideBorders() = Borders(
        icon = MaterialTheme.bordersExtended.icon.normal,
    )

    internal val localBorders: ProvidableCompositionLocal<Borders> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Typographies(
        val title: OutfitTextStateColor,
        val body: OutfitTextStateColor,
    )

    @Composable
    fun provideTypographies() = Typographies(
        title = MaterialTheme.typographiesExtended.title.big.copy {
            outfitState = colors.primary.asStateSimple
        },
        body = MaterialTheme.typographiesExtended.body.normal.copy {
            outfitState = colors.dark.asStateSimple
        },

        )

    internal val localTypographies: ProvidableCompositionLocal<Typographies> =
        staticCompositionLocalOf {
            error("not provided")
        }


    data class Style(
        val iconLogOut: Icon.StateColor.Style,
        val iconUser: Image.StateColor.Style,
        val sectionRow: SectionSimpleRow.Style,

        )

    @Composable
    fun provideStyles() = Style(
        iconLogOut = Icon.StateColor.Style(
            size = MaterialTheme.dimensionsIconExtended.modal.normal,
            tint = colors.background,
            outfitFrame = shapes.icon.copy {
                outfitState = colors.primary.asStateSimple
            }.asFrameStateColor
        ),
        iconUser = Image.StateColor.Style(
            size = dimensions.iconUser,
            outfitFrame = OutfitFrameStateColor(
                outfitShape = shapes.icon,
                outfitBorder = borders.icon.copy {
                    outfitState = colors.decor.asStateSimple
                }
            )
        ),
        sectionRow = ThemeComponentProviders.sectionSimpleRowStyle().copy {
            paddingBody = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal
        },
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}