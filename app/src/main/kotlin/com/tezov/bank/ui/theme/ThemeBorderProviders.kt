

package com.tezov.bank.ui.theme

import androidx.compose.ui.unit.dp
import com.tezov.lib_adr_ui_core.theme.style.OutfitBorder.StateColor.Style.Companion.asPaletteSize
import com.tezov.lib_adr_ui_core.theme.style.OutfitBorder.StateColor.Style.Companion.asStateColor
import com.tezov.lib_adr_ui_core.theme.style.OutfitPaletteSize
import com.tezov.lib_adr_ui_core.theme.theme.ThemeBordersExtended

object ThemeBorderProviders {

    fun common() = ThemeBordersExtended.Common(
        cluster = 1.2.dp.asStateColor.asPaletteSize,
        block = OutfitPaletteSize(
            normal = 1.dp.asStateColor,
            small = 0.85.dp.asStateColor,
        ),
        element = OutfitPaletteSize(
            normal = 0.9.dp.asStateColor,
            big = 1.1.dp.asStateColor
        ),
        chunk = 0.8.dp.asStateColor.asPaletteSize,
        button = OutfitPaletteSize(
            normal = 1.2.dp.asStateColor,
            big = 2.5.dp.asStateColor,
        ),
        icon = OutfitPaletteSize(
            normal = 2.5.dp.asStateColor,
        ),
    )


}