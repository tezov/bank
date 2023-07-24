

package com.tezov.bank.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.tezov.lib_core_android_kotlin.ui.type.primaire.DpSize
import com.tezov.lib_core_android_kotlin.ui.type.primaire.dpSize
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitPalette.Size.Style.Companion.asPaletteSize
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitPaletteDirection
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitPaletteSize
import com.tezov.lib_core_android_kotlin.ui.theme.theme.ThemeDimensionsExtended

object ThemeDimensionProviders {

    fun common() = ThemeDimensionsExtended.Common(
        elevation = OutfitPaletteSize(
            normal = 2.dp,
            micro = 1.dp,
            small = 1.5.dp,
            big = 3.5.dp,
        ),
        divider = OutfitPaletteSize(
            normal = 1.dp,
            small = 0.8.dp,
            big = 1.2.dp,
            huge = 2.5.dp,
        ),
    )

    fun paddings() = ThemeDimensionsExtended.Paddings(
        page = OutfitPaletteSize(
            normal = OutfitPaletteDirection(
                vertical = 8.dp,
                horizontal = 10.dp
            ),
            small = OutfitPaletteDirection(
                vertical = 4.dp,
                horizontal = 6.dp
            ),
            big = OutfitPaletteDirection(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
            huge = OutfitPaletteDirection(
                vertical = 22.dp,
                horizontal = 22.dp
            ),
        ),
        cluster = OutfitPaletteSize(
            normal = OutfitPaletteDirection(
                vertical = 10.dp,
                horizontal = 8.dp
            ),
        ),
        block = OutfitPaletteSize(
            normal = OutfitPaletteDirection(
                vertical = 10.dp,
                horizontal = 6.dp
            ),
            small = OutfitPaletteDirection(
                vertical = 4.dp,
                horizontal = 4.dp
            ),
            big = OutfitPaletteDirection(
                vertical = 16.dp,
                horizontal = 10.dp
            ),
            huge = OutfitPaletteDirection(
                vertical = 22.dp,
                horizontal = 14.dp
            ),
        ),
        element = OutfitPaletteSize(
            small = OutfitPaletteDirection(
                vertical = 3.dp,
                horizontal = 4.dp
            ),
            normal = OutfitPaletteDirection(
                vertical = 6.dp,
                horizontal = 8.dp
            ),
            big = OutfitPaletteDirection(
                vertical = 12.dp,
                horizontal = 14.dp
            ),
            huge = OutfitPaletteDirection(
                vertical = 18.dp,
                horizontal = 24.dp
            ),
            supra = OutfitPaletteDirection(
                vertical = 24.dp,
                horizontal = 30.dp
            )
        ),
        chunk = OutfitPaletteSize(
            normal = OutfitPaletteDirection(
                vertical = 4.dp,
                horizontal = 3.dp
            ),
            small = OutfitPaletteDirection(
                vertical = 2.dp,
                horizontal = 1.5.dp
            )
        ),
        button = OutfitPaletteDirection(
            vertical = 6.dp,
            horizontal = 10.dp,
        ).asPaletteSize,
        text = OutfitPaletteSize(
            normal = OutfitPaletteDirection(
                vertical = 4.dp,
                horizontal = 6.dp
            ),
            small = OutfitPaletteDirection(
                vertical = 2.dp,
                horizontal = 3.dp
            ),
            big = OutfitPaletteDirection(
                vertical = 6.dp,
                horizontal = 9.dp
            )
        )
    )

    fun icons() = ThemeDimensionsExtended.Icons(
        modal = OutfitPaletteSize(
            normal = DpSize(
                width = 42.dp,
                height = 42.dp,
                padding = PaddingValues(10.dp),
            ),
            small = DpSize(
                width = 18.dp,
                height = 18.dp,
                padding = PaddingValues(2.dp),
            )
        ),
        info = OutfitPaletteSize(
            normal = 28.dpSize,
            micro = DpSize(
                width = 16.dp,
                height = 16.dp,
                padding = PaddingValues(2.dp),
            ),
            small = DpSize(
                width = 24.dp,
                height = 24.dp,
                padding = PaddingValues(3.dp),
            ),
            big = 54.dpSize,
            huge = 86.dpSize,
        ),
        action = OutfitPaletteSize(
            normal = 22.dpSize,
            small = 16.dpSize,
            big = DpSize(
                width = 36.dp,
                height = 36.dp,
                padding = PaddingValues(8.dp),
            ),
        ),
        fieldInfo = 22.dpSize.asPaletteSize,
        fieldAction = 32.dpSize.asPaletteSize,
    )
}