

package com.tezov.bank.ui.component.element

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Icon
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Icon.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Text
import com.tezov.lib_adr_ui_core.modifier.thenOnNotNull
import com.tezov.lib_adr_ui_core.theme.style.OutfitFrame.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitShape.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_ui_core.theme.theme.shapesExtended
import com.tezov.lib_kmm_core.delegate.DelegateNullFallBack

object AccountValueSimpleRow {

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var styleIconInfo = style.styleIconInfo
        var outfitTextTitle = style.outfitTextTitle
        var outfitTextSubTitle = style.outfitTextSubTitle
        var outfitTextAmount = style.outfitTextAmount
        var background = style.background

        fun get() = Style(
            styleIconInfo = styleIconInfo,
            outfitTextTitle = outfitTextTitle,
            outfitTextSubTitle = outfitTextSubTitle,
            outfitTextAmount = outfitTextAmount,
            background = background,
        )
    }

    class Style(
        styleIconInfo: Icon.StateColor.Style? = null,
        val outfitTextTitle: OutfitTextStateColor? = null,
        val outfitTextSubTitle: OutfitTextStateColor? = null,
        val outfitTextAmount: OutfitTextStateColor? = null,
        val background: Color? = null,
    ) {
        val styleIconInfo: Icon.StateColor.Style by DelegateNullFallBack.Ref(
            styleIconInfo,
            fallBackValue = {
                Icon.StateColor.Style()
            }
        )

        companion object {

            @Composable
            fun Style.copy(
                scope: @Composable StyleBuilder.() -> Unit = {}
            ) = StyleBuilder(this).also {
                it.scope()
            }.get()

        }

        constructor(style: Style) : this(
            styleIconInfo = style.styleIconInfo,
            outfitTextTitle = style.outfitTextTitle,
            outfitTextSubTitle = style.outfitTextSubTitle,
            outfitTextAmount = style.outfitTextAmount,
            background = style.background,
        )

        @Composable
        internal fun styleIconInfo(color: Color?) = color?.let {
            styleIconInfo.copy {
                outfitFrame = outfitFrame?.copy {
                    outfitShape = outfitShape.copy {
                        outfitState = it.asStateSimple
                    }
                }
            }
        } ?: run {
            styleIconInfo
        }

    }

    class Data(
        val iconInfoId: Int,
        val iconInfoColor: Color? = null,
        val title: String,
        val subTitle: String? = null,
        val amount: String,
    )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        onClick: () -> Unit = {}
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .thenOnNotNull(style.background) {
                    background(it)
                }
                .clip(MaterialTheme.shapesExtended.clip.normal)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon.StateColor(
                style = style.styleIconInfo(color = data.iconInfoColor),
                resourceId = data.iconInfoId,
                description = null,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimensionsPaddingExtended.element.normal.horizontal))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        end = MaterialTheme.dimensionsPaddingExtended.element.normal.horizontal,
                    )
            ) {
                Text.StateColor(
                    text = data.title,
                    style = style.outfitTextTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                data.subTitle?.let {
                    Text.StateColor(
                        text = it,
                        style = style.outfitTextSubTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Text.StateColor(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Top),
                text = data.amount,
                style = style.outfitTextAmount
            )
        }
    }

}