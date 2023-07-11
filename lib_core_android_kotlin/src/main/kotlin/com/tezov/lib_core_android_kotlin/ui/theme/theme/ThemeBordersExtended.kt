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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitBorder.StateColor.Style.Companion.asPaletteSize
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitBorderStateColor
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitPaletteSize
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_core_kotlin.delegate.DelegateNullFallBack

val MaterialTheme.bordersExtended
    @Composable
    @ReadOnlyComposable
    get() = ThemeBordersExtended.localBorders.current

infix fun MaterialTheme.provides(value: ThemeBordersExtended.Common) =
    ThemeBordersExtended.localBorders provides value

object ThemeBordersExtended {

    class Common(
        cluster: OutfitPaletteSize<OutfitBorderStateColor>? = null,
        block: OutfitPaletteSize<OutfitBorderStateColor>? = null,
        element: OutfitPaletteSize<OutfitBorderStateColor>? = null,
        chunk: OutfitPaletteSize<OutfitBorderStateColor>? = null,
        button: OutfitPaletteSize<OutfitBorderStateColor>? = null,
        icon: OutfitPaletteSize<OutfitBorderStateColor>? = null,
    ) {

        private val delegates =
            DelegateNullFallBack.Group<OutfitPaletteSize<OutfitBorderStateColor>>()
        val cluster: OutfitPaletteSize<OutfitBorderStateColor> by delegates.ref(cluster)
        val block: OutfitPaletteSize<OutfitBorderStateColor> by delegates.ref(block)
        val element: OutfitPaletteSize<OutfitBorderStateColor> by delegates.ref(element)
        val chunk: OutfitPaletteSize<OutfitBorderStateColor> by delegates.ref(chunk)
        val button: OutfitPaletteSize<OutfitBorderStateColor> by delegates.ref(button)
        val icon: OutfitPaletteSize<OutfitBorderStateColor> by delegates.ref(icon)

        init {
            delegates.fallBackValue = {
                ThemeColorsExtended.Dummy.outfitBorderState.asPaletteSize
            }
        }

    }

    internal val localBorders: ProvidableCompositionLocal<Common> = staticCompositionLocalOf {
        error("not provided")
    }


}