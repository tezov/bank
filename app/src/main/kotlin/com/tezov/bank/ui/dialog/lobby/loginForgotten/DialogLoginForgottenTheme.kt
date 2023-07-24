

package com.tezov.bank.ui.dialog.lobby.loginForgotten

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.theme.colorsExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.typographiesExtended

val DialogLoginForgottenTheme.colors: DialogLoginForgottenTheme.Colors
    @Composable
    @ReadOnlyComposable
    get() = localColors.current

infix fun DialogLoginForgottenTheme.provides(value: DialogLoginForgottenTheme.Colors) =
    localColors provides value

val DialogLoginForgottenTheme.typographies: DialogLoginForgottenTheme.Typographies
    @Composable
    @ReadOnlyComposable
    get() = localTypographies.current

infix fun DialogLoginForgottenTheme.provides(value: DialogLoginForgottenTheme.Typographies) =
    localTypographies provides value

val DialogLoginForgottenTheme.styles: DialogLoginForgottenTheme.Style
    @Composable
    @ReadOnlyComposable
    get() = localStyles.current

infix fun DialogLoginForgottenTheme.provides(value: DialogLoginForgottenTheme.Style) =
    localStyles provides value

object DialogLoginForgottenTheme {

    data class Colors(
        val onBackground: Color,
        val light: Color,
        val accent: Color,
    )

    @Composable
    fun provideColors() = Colors(
        onBackground = MaterialTheme.colorsExtended.onBackgroundElevated.default,
        light = MaterialTheme.colorsExtended.primary.light,
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
            outfitState = colors.onBackground.asStateSimple
        },
        body = MaterialTheme.typographiesExtended.body.small.copy {
            outfitState = colors.light.asStateSimple
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
        linkConfirm = MaterialTheme.typographiesExtended.button.small.copy {
            outfitState = colors.accent.asStateSimple
        },
        linkCancel = MaterialTheme.typographiesExtended.button.small.copy {
            outfitState = colors.accent.asStateSimple
        },
    )

    internal val localStyles: ProvidableCompositionLocal<Style> = staticCompositionLocalOf {
        error("not provided")
    }

}