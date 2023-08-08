

package com.tezov.bank.ui.component.element

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tezov.app.R
import com.tezov.bank.ui.component.element.MessageRow.Style.Companion.ICON_INFO_SIZE
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Icon
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Text
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_ui_core.theme.theme.ThemeColorsExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_ui_core.theme.theme.shapesExtended
import com.tezov.lib_kmm_core.delegate.DelegateNullFallBack

object MessageRow {

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var colorBadge = style.colorBadge
        var styleIconAction = style.styleIconAction
        var outfitTextTitle = style.outfitTextTitle
        var outfitTextSubTitle = style.outfitTextSubtitle

        fun get() = Style(
            colorBadge = colorBadge,
            styleIconAction = styleIconAction,
            outfitTextTitle = outfitTextTitle,
            outfitTextSubtitle = outfitTextSubTitle,
        )
    }

    class Style(
        colorBadge: Color = ThemeColorsExtended.Dummy.pink,
        styleIconAction: Icon.Simple.Style? = null,
        val outfitTextTitle: OutfitTextStateColor? = null,
        val outfitTextSubtitle: OutfitTextStateColor? = null,
    ) {

        val colorBadge: Color by DelegateNullFallBack.Ref(
            colorBadge,
            fallBackValue = {
                ThemeColorsExtended.Dummy.pink
            }
        )

        val styleIconAction: Icon.Simple.Style by DelegateNullFallBack.Ref(
            styleIconAction,
            fallBackValue = {
                Icon.Simple.Style()
            }
        )

        companion object {

            internal val ICON_INFO_SIZE = 8.dp

            @Composable
            fun Style.copy(
                scope: @Composable StyleBuilder.() -> Unit = {}
            ) = StyleBuilder(this).also {
                it.scope()
            }.get()

        }

        constructor(style: Style) : this(
            colorBadge = style.colorBadge,
            styleIconAction = style.styleIconAction,
            outfitTextTitle = style.outfitTextTitle,
            outfitTextSubtitle = style.outfitTextSubtitle,
        )

    }

    class Data(
        val title: String,
        val subtitle: String,
        val iconActionId: Int = R.drawable.ic_arrow_cut_right_24dp,
        val active: Boolean = false
    )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        onClick: () -> Unit = {}
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapesExtended.clip.normal)
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (data.active) {
                    Box(
                        modifier = Modifier
                            .background(style.colorBadge, CircleShape)
                            .size(ICON_INFO_SIZE)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimensionsPaddingExtended.chunk.small.horizontal))
                }
                Text.StateColor(
                    modifier = Modifier.weight(1f),
                    text = data.title,
                    style = if (data.active) {
                        style.outfitTextTitle?.copy{
                            typo = typo.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        style.outfitTextTitle
                    }
                )
                Icon.Simple(
                    style = style.styleIconAction,
                    resourceId = data.iconActionId,
                    description = data.title,
                )
            }
            Text.StateColor(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.subtitle,
                style = style.outfitTextSubtitle
            )
        }


    }

}