

package com.tezov.bank.ui.dialog.auth.closeAppConfirmation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.theme.colorsExtended
import com.tezov.lib_adr_sdk_core.ui.theme.theme.typographiesExtended

val DialogCloseAppConfirmationTheme.colors: DialogCloseAppConfirmationTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun DialogCloseAppConfirmationTheme.provides(value: DialogCloseAppConfirmationTheme.Colors) =
    localColors provides value

val DialogCloseAppConfirmationTheme.typographies: DialogCloseAppConfirmationTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun DialogCloseAppConfirmationTheme.provides(value: DialogCloseAppConfirmationTheme.Typographies) =
    localTypographies provides value

val DialogCloseAppConfirmationTheme.styles: DialogCloseAppConfirmationTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun DialogCloseAppConfirmationTheme.provides(value: DialogCloseAppConfirmationTheme.Style) =
    localStyles provides value

object DialogCloseAppConfirmationTheme {

    data class Colors(
        val onBackground: Color,
        val light: Color,
        val accent: Color,
    )

    @Composable
    fun provideColors() = Colors(
        onBackground = MaterialTheme.colorsExtended.onBackgroundElevated.default,
        light = MaterialTheme.colorsExtended.primary.default,
        accent = MaterialTheme.colorsExtended.primary.accent,
    )

    internal val localColors: ProvidableCompositionLocal<Colors> = staticCompositionLocalOf {
        error("not provided")
    }

    data class Typographies(
        val title: OutfitTextStateColor,
        val body: OutfitTextStateColor,
    )

    @Composable
    fun provideTypographies() = Typographies(
        title = MaterialTheme.typographiesExtended.title.big.copy {
            outfitState = colors.accent.asStateSimple
        },
        body = MaterialTheme.typographiesExtended.body.normal.copy {
            outfitState = colors.onBackground.asStateSimple
        },
    )

    internal val localTypographies: ProvidableCompositionLocal<Typographies> =
        staticCompositionLocalOf {
            error("not provided")
        }

    data class Style(
        val linkConfirm: OutfitTextStateColor,
        val linkCancel: OutfitTextStateColor,
    )

    @Composable
    fun provideStyles() = Style(
        linkConfirm = MaterialTheme.typographiesExtended.button.normal.copy {
            outfitState = colors.accent.asStateSimple
        },
        linkCancel = MaterialTheme.typographiesExtended.button.normal.copy {
            outfitState = colors.light.asStateSimple
        },
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}