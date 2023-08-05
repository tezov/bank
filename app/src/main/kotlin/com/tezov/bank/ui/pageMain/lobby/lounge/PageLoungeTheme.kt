

package com.tezov.bank.ui.pageMain.lobby.lounge

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.lib_adr_sdk_core.ui.type.primaire.DpSize
import com.tezov.lib_adr_sdk_core.ui.type.primaire.dpSize
import com.tezov.lib_adr_sdk_core.ui.component.block.HorizontalPager
import com.tezov.lib_adr_sdk_core.ui.component.block.HorizontalPager.WithIndicator.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.component.chunk.*
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Button.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.component.chunk.DropDownMenu.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.*
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitBorder.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitFrame.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitShape.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.theme.*

val PageLoungeTheme.colors: PageLoungeTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageLoungeTheme.provides(value: PageLoungeTheme.Colors) = localColors provides value

val PageLoungeTheme.dimensions: PageLoungeTheme.Dimensions
    @Composable
    @ReadOnlyComposable
    get() = localDimensions.current

infix fun PageLoungeTheme.provides(value: PageLoungeTheme.Dimensions) = localDimensions provides value

val PageLoungeTheme.shapes: PageLoungeTheme.Shapes
    @Composable
    @ReadOnlyComposable
    get() = localShapes.current

infix fun PageLoungeTheme.provides(value: PageLoungeTheme.Shapes) = localShapes provides value

val PageLoungeTheme.borders: PageLoungeTheme.Borders
    @Composable
    @ReadOnlyComposable
    get() = localBorders.current

infix fun PageLoungeTheme.provides(value: PageLoungeTheme.Borders) = localBorders provides value

val PageLoungeTheme.typographies: PageLoungeTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun PageLoungeTheme.provides(value: PageLoungeTheme.Typographies) =
    localTypographies provides value

val PageLoungeTheme.styles: PageLoungeTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageLoungeTheme.provides(value: PageLoungeTheme.Style) = localStyles provides value

object PageLoungeTheme {

    data class Colors(
        val background: Color,
        val onBackground: Color,
        val dark: Color,
        val light: Color,
        val fade: Color,
    )

    @Composable
    fun provideColors() = Colors(
        background = MaterialTheme.colorsExtended.background.accent,
        onBackground = MaterialTheme.colorsExtended.onBackground.accent,
        dark = MaterialTheme.colorsExtended.primary.default,
        light = MaterialTheme.colorsExtended.primary.shiny.copy(alpha = 0.65f),
        fade = MaterialTheme.colorsExtended.primary.fade.copy(alpha = 0.35f),
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Dimensions(
        val spacingTopToTitle: Dp,
        val spacingTopFromLinkService: Dp,
        val logo: DpSize,
        val iconBig: DpSize,
        val paddingStartToIconBig: Dp,
        val iconMedium: DpSize,
        val paddingStartToIconMedium: Dp,
        val iconSmall: DpSize,
        val starSmall: Dp,
        val starBig: Dp,
    )

    @Composable
    fun provideDimensions() = Dimensions(
        spacingTopToTitle = MaterialTheme.dimensionsPaddingExtended.element.huge.vertical,
        spacingTopFromLinkService = MaterialTheme.dimensionsPaddingExtended.element.supra.vertical,
        logo = 58.dpSize,
        iconBig = 48.dpSize,
        paddingStartToIconBig = MaterialTheme.dimensionsPaddingExtended.element.normal.horizontal,
        iconMedium = 32.dpSize,
        paddingStartToIconMedium = MaterialTheme.dimensionsPaddingExtended.element.normal.horizontal,
        iconSmall = 38.dpSize,
        starSmall = 10.dp,
        starBig = 15.dp,
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
        val supra: OutfitTextStateColor,
        val huge: OutfitTextStateColor,
        val title: OutfitTextStateColor,
        val headline: OutfitTextStateColor,
        val body: OutfitTextStateColor,
        val label: OutfitTextStateColor,
        val note: OutfitTextStateColor,
    )

    @Composable
    fun provideTypographies() = Typographies(
        supra = MaterialTheme.typographiesExtended.title.supra.copy {
            outfitState = colors.onBackground.asStateSimple
        },
        huge = MaterialTheme.typographiesExtended.title.huge.copy {
            outfitState = colors.onBackground.asStateSimple
            typo = typo.copy(
                fontWeight = FontWeight.Bold
            )
        },
        title = MaterialTheme.typographiesExtended.title.big.copy {
            outfitState = colors.onBackground.asStateSimple
            typo = typo.copy(fontWeight = FontWeight.Bold, letterSpacing = 4.sp)
        },
        headline = MaterialTheme.typographiesExtended.title.supra.copy {
            outfitState = colors.onBackground.asStateSimple
            typo = typo.copy(fontSize = 48.sp, fontWeight = FontWeight.Bold)
        },
        body = MaterialTheme.typographiesExtended.body.big.copy {
            outfitState = colors.onBackground.asStateSimple
            typo = typo.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
        },
        label = MaterialTheme.typographiesExtended.body.normal.copy {
            outfitState = colors.onBackground.asStateSimple
        },
        note = MaterialTheme.typographiesExtended.label.small.copy {
            outfitState = colors.onBackground.asStateSimple
            typo = typo.copy(fontWeight = FontWeight.Bold)
        },
    )

    internal val localTypographies: ProvidableCompositionLocal<Typographies> =
        staticCompositionLocalOf {
            error("not provided")
        }

    data class Style(
        val dropDownMenu: DropDownMenu.StateColor.Style,
        val pager: HorizontalPager.WithIndicator.Style,
        val buttonDark: Button.StateColor.Style,
        val buttonLight: Button.StateColor.Style,
        val buttonOutlined: Button.StateColor.Style,
        val link: OutfitTextStateColor,
        val logo: Image.Simple.Style,
        val iconBig: Image.StateColor.Style,
        val iconMedium: Icon.StateColor.Style,
    )

    @Composable
    fun provideStyles() = Style(
        dropDownMenu =ThemeComponentProviders.dropDownMenu().copy{
            styleIcon = Icon.StateColor.Style(
                size = dimensions.iconSmall,
                tint = colors.onBackground,
                outfitFrame = OutfitFrameStateColor(
                    outfitShape = shapes.icon.copy {
                        outfitState = colors.dark.asStateSimple
                    }
                )
            )
        },
        pager = ThemeComponentProviders.pagerWidthIndicatorStyle().copy {
            outfitShapeIndicator = OutfitShapeStateColor(
                outfitState = OutfitStateBiStable(
                    active = colors.dark,
                    inactive = colors.dark.copy(alpha = 0.45f),
                )
            )
        },
        buttonDark = MaterialTheme.componentsButtonExtended.primary.copy {
            outfitFrame = outfitFrame.copy {
                outfitShape = outfitShape.copy {
                    outfitState = colors.dark.asStateSimple
                }
            }
            outfitText = outfitText.copy {
                outfitState = colors.onBackground.asStateSimple
            }
        },
        buttonLight = MaterialTheme.componentsButtonExtended.secondary.copy {
            outfitFrame = outfitFrame.copy {
                outfitShape = outfitShape.copy {
                    outfitState = colors.onBackground.asStateSimple
                }
                outfitBorder = outfitBorder.copy {
                    outfitState = colors.fade.asStateSimple
                }
            }
            outfitText = outfitText.copy {
                outfitState = colors.dark.asStateSimple

            }
        },
        buttonOutlined = MaterialTheme.componentsButtonExtended.tertiary.copy {
            outfitFrame = outfitFrame.copy {
                outfitBorder = outfitBorder.copy {
                    outfitState = colors.onBackground.asStateSimple
                }
            }
            outfitText = outfitText.copy {
                outfitState = colors.onBackground.asStateSimple
            }
        },
        link = MaterialTheme.componentsLinkExtended.secondary.copy {
            outfitState = colors.onBackground.asStateSimple
        },
        logo = Image.Simple.Style(
            size = dimensions.logo,
            contentScale = ContentScale.Fit
        ),
        iconBig = Image.StateColor.Style(
            size = dimensions.iconBig,
            outfitFrame = OutfitFrameStateColor(
                outfitShape = shapes.icon,
                outfitBorder = borders.icon.copy {
                    outfitState = colors.light.asStateSimple
                }
            )
        ),
        iconMedium = Icon.StateColor.Style(
            size = dimensions.iconMedium,
            tint = colors.background,
            outfitFrame = OutfitFrameStateColor(
                outfitShape = shapes.icon.copy {
                    outfitState = colors.onBackground.asStateSimple
                }
            )
        )
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}