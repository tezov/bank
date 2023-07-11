/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.theme.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.tezov.lib_core_android_kotlin.navigation.bottom_navigation.BottomNavigation
import com.tezov.lib_core_android_kotlin.navigation.top_app_bar.TopAppBar
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheet
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.dialog.Dialog
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.Snackbar
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Button as ButtonImport

val MaterialTheme.componentsCommonExtended
    @Composable
    @ReadOnlyComposable
    get() = ThemeComponentExtended.localCommons.current

infix fun MaterialTheme.provides(value: ThemeComponentExtended.Common) =
    ThemeComponentExtended.localCommons provides value

val MaterialTheme.componentsButtonExtended
    @Composable
    @ReadOnlyComposable
    get() = ThemeComponentExtended.localButtons.current

infix fun MaterialTheme.provides(value: ThemeComponentExtended.Buttons) =
    ThemeComponentExtended.localButtons provides value

val MaterialTheme.componentsLinkExtended
    @Composable
    @ReadOnlyComposable
    get() = ThemeComponentExtended.localLinks.current

infix fun MaterialTheme.provides(value: ThemeComponentExtended.Links) =
    ThemeComponentExtended.localLinks provides value

object ThemeComponentExtended {

    class Common(
        topAppBar: TopAppBar.Style? = null,
        bottomNavigation: BottomNavigation.Style? = null,
        dialogCard: Dialog.Card.Style? = null,
        bottomSheet: BottomSheet.Sheet.Style? = null,
        snackBar: Snackbar.Style? = null,
    ) {
        val topAppBar: TopAppBar.Style by DelegateNullFallBack.Ref(topAppBar)
        val bottomNavigation: BottomNavigation.Style by DelegateNullFallBack.Ref(bottomNavigation)
        val dialogCard: Dialog.Card.Style by DelegateNullFallBack.Ref(dialogCard)
        val bottomSheet: BottomSheet.Sheet.Style by DelegateNullFallBack.Ref(bottomSheet)
        val snackBar: Snackbar.Style by DelegateNullFallBack.Ref(snackBar)
    }

    internal val localCommons: ProvidableCompositionLocal<Common> = staticCompositionLocalOf {
        error("not provided")
    }


    class Buttons(
        val primary: ButtonImport.StateColor.Style,
        secondary: ButtonImport.StateColor.Style? = null,
        tertiary: ButtonImport.StateColor.Style? = null,
        confirm: ButtonImport.StateColor.Style? = null,
        cancel: ButtonImport.StateColor.Style? = null,
        proceed: ButtonImport.StateColor.Style? = null,
    ) {

        private val delegates = DelegateNullFallBack.Group<ButtonImport.StateColor.Style>()
        val secondary: ButtonImport.StateColor.Style by delegates.ref(secondary)
        val tertiary: ButtonImport.StateColor.Style by delegates.ref(tertiary)
        val confirm: ButtonImport.StateColor.Style by delegates.ref(confirm)
        val cancel: ButtonImport.StateColor.Style by delegates.ref(cancel)
        val proceed: ButtonImport.StateColor.Style by delegates.ref(proceed)

        init {
            delegates.fallBackValue = { primary }
        }
    }

    internal val localButtons: ProvidableCompositionLocal<Buttons> = staticCompositionLocalOf {
        error("not provided")
    }

    class Links(
        val primary: OutfitTextStateColor,
        secondary: OutfitTextStateColor? = null,
        tertiary: OutfitTextStateColor? = null,
    ) {

        private val delegates = DelegateNullFallBack.Group<OutfitTextStateColor>()
        val secondary: OutfitTextStateColor by delegates.ref(secondary)
        val tertiary: OutfitTextStateColor by delegates.ref(tertiary)

        init {
            delegates.fallBackValue = { primary }
        }
    }

    internal val localLinks: ProvidableCompositionLocal<Links> = staticCompositionLocalOf {
        error("not provided")
    }
}