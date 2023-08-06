

package com.tezov.bank.ui.theme

import androidx.compose.ui.unit.dp
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitPaletteSize
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitShape.StateColor.Style.Companion.asStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.theme.ThemeShapesExtended

object ThemeShapeProviders {

    fun common() = ThemeShapesExtended.Common(
        block = OutfitPaletteSize(
            normal = 10.dp.asStateColor,
            small = 6.dp.asStateColor,
            big = 14.dp.asStateColor,
        ),
        element = OutfitPaletteSize(
            normal = 12.dp.asStateColor,
            small = 5.dp.asStateColor,
            big = 18.dp.asStateColor,
        ),
        button = OutfitPaletteSize(
            normal = 18.dp.asStateColor,
            small = 10.dp.asStateColor,
        ),
        icon = OutfitPaletteSize(
            normal = 50.asStateColor
        )
    )


}