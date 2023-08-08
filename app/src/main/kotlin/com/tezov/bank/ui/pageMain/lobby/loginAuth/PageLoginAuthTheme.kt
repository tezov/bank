

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tezov.bank.ui.theme.ThemeComponentProviders
import com.tezov.bank.ui.theme.font.fontRoboto
import com.tezov.lib_adr_app_core.ui.theme.theme.componentsButtonExtended
import com.tezov.lib_adr_app_core.ui.theme.theme.componentsLinkExtended
import com.tezov.lib_adr_ui_core.type.primaire.QuadDirection
import com.tezov.lib_adr_ui_cpt.core.component.block.KeyBoard
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Button
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Button.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.core.component.chunk.TextField
import com.tezov.lib_adr_ui_core.theme.style.OutfitBorder.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitFrame.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitFrameStateColor
import com.tezov.lib_adr_ui_core.theme.style.OutfitShape
import com.tezov.lib_adr_ui_core.theme.style.OutfitShape.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.OutfitStateBiStable
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_ui_core.theme.theme.*

val PageLoginAuthTheme.colors: PageLoginAuthTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun PageLoginAuthTheme.provides(value: PageLoginAuthTheme.Colors) =
    localColors provides value

val PageLoginAuthTheme.typographies: PageLoginAuthTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun PageLoginAuthTheme.provides(value: PageLoginAuthTheme.Typographies) =
    localTypographies provides value

val PageLoginAuthTheme.styles: PageLoginAuthTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun PageLoginAuthTheme.provides(value: PageLoginAuthTheme.Style) =
    localStyles provides value

object PageLoginAuthTheme {

    data class Colors(
        val background: Color,
        val onBackground: Color,
        val dark: Color,
        val light: Color,
        val fade: Color,
    )

    @Composable
    fun provideColors() = Colors(
        background = MaterialTheme.colorsExtended.background.overlay,
        onBackground = MaterialTheme.colorsExtended.onBackground.overlay,
        dark = MaterialTheme.colorsExtended.primary.shady,
        light = MaterialTheme.colorsExtended.primary.accent,
        fade = MaterialTheme.colorsExtended.primary.fade,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Typographies(
        val title: OutfitTextStateColor,
    )

    @Composable
    fun provideTypographies() = Typographies(
        title = MaterialTheme.typographiesExtended.title.big.copy {
            outfitState = colors.onBackground.asStateSimple
            typo = typo.copy(textAlign = TextAlign.Center)
        },
    )

    internal val localTypographies: ProvidableCompositionLocal<Typographies> =
        staticCompositionLocalOf {
            error("not provided")
        }

    data class Style(
        val field: TextField.StateColor.Style,
        val keyBoardGridCube: KeyBoard.GridCube.Style,
        val button: Button.StateColor.Style,
        val link: OutfitTextStateColor,
    )

    @Composable
    fun provideStyles() = Style(
        field = ThemeComponentProviders.textFieldLight(),
        keyBoardGridCube = KeyBoard.GridCube.Style(
            outfitText = OutfitTextStateColor(
                typo = TextStyle(
                    fontFamily = MaterialTheme.fontRoboto,
                    fontWeight = FontWeight.SemiBold,
                ),
                outfitState = colors.onBackground.asStateSimple
            ),
            outfitFrameOuter = OutfitFrameStateColor(
                outfitBorder = MaterialTheme.bordersExtended.block.normal.copy {
                    outfitState = colors.onBackground.asStateSimple
                    size?.let {
                        size = (it.value * 0.65f).dp
                    }
                },
                outfitShape = MaterialTheme.shapesExtended.block.normal.copy {
                    size?.firstNotNull?.let {
                        size = OutfitShape.Size(bottomStart = it, bottomEnd = it)
                    }
                }
            ),
            outfitBorderInner = MaterialTheme.bordersExtended.block.small.copy {
                outfitState = colors.onBackground.asStateSimple
            },
            drawOutsideLine = QuadDirection(
                top = true,
                start = false,
                bottom = false,
                end = false
            )
        ),
        button = MaterialTheme.componentsButtonExtended.primary.copy {
            outfitFrame = outfitFrame.copy {
                outfitShape = outfitShape.copy {
                    outfitState = OutfitStateBiStable(
                        active = colors.light,
                        inactive = colors.dark
                    )
                }
            }
            outfitText = outfitText.copy {
                outfitState = colors.onBackground.asStateSimple
            }
        },
        link = MaterialTheme.componentsLinkExtended.primary.copy {
            outfitState = colors.onBackground.asStateSimple
        }
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }


}