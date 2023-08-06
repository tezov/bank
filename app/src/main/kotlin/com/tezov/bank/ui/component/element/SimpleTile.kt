

package com.tezov.bank.ui.component.element

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon.Simple.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.theme.style.*
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_adr_core.delegate.DelegateNullFallBack

object SimpleTile {

    enum class Template {
        Undefined,
        IconTopEnd,
        IconTop,
        IconEnd;

        val isInlined get() = this == IconEnd
        val isUndefined get() = this == Undefined
        val isDefined get() = this != Undefined

        companion object {
            val InlineDefault get() = IconEnd
        }
    }

    class StyleBuilder internal constructor(
        style: Style
    ) {
        var outfitFrame = style.outfitFrame
        var styleIconInfo = style.styleIconInfo
        var outfitTextTitle = style.outfitTextTitle
        var outfitTextSubtitle = style.outfitTextSubtitle

        fun get() = Style(
            outfitFrame = outfitFrame,
            styleIconInfo = styleIconInfo,
            outfitTextTitle = outfitTextTitle,
            outfitTextSubtitle = outfitTextSubtitle,
        )
    }

    class Style(
        outfitFrame: OutfitFrameStateColor? = null,
        styleIconInfo: Icon.Simple.Style? = null,
        val outfitTextTitle: OutfitTextStateColor? = null,
        val outfitTextSubtitle: OutfitTextStateColor? = null,
    ) {
        val outfitFrame: OutfitFrameStateColor by DelegateNullFallBack.Ref(
            outfitFrame,
            fallBackValue = {
                ThemeColorsExtended.Dummy.outfitFrameState
            }
        )

        val styleIconInfo: Icon.Simple.Style by DelegateNullFallBack.Ref(
            styleIconInfo,
            fallBackValue = {
                Icon.Simple.Style()
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
            outfitFrame = style.outfitFrame,
            styleIconInfo = style.styleIconInfo,
            outfitTextTitle = style.outfitTextTitle,
            outfitTextSubtitle = style.outfitTextSubtitle,
        )

        @Composable
        internal fun styleIconInfo(color: Color?) = color?.let {
            styleIconInfo.copy {
                tint = it
            }
        } ?: run {
            styleIconInfo
        }

    }

    data class Data(
        var template: Template = Template.Undefined,
        val title: String,
        val subtitle: String? = null,
        val iconInfoId: Int,
        val iconInfoColor: Color? = null,

        )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style,
        data: Data,
        onClick: () -> Unit = {}
    ) {
        when (data.template) {
            Template.IconTopEnd, Template.Undefined -> {
                ContentIconTopEnd(style, modifier, data, onClick)
            }
            Template.IconTop -> {
                ContentIconTop(style, modifier, data, onClick)
            }
            Template.IconEnd -> {
                ContentIconEnd(style, modifier, data, onClick)
            }
        }
    }

    @Composable
    private fun ContentIconTopEnd(
        style: Style,
        modifier: Modifier,
        data: Data,
        onClick: () -> Unit = {}
    ) {
        Column(
            modifier = modifier
                .background(style.outfitFrame)
                .clickable { onClick() }
                .padding(MaterialTheme.dimensionsPaddingExtended.element.normal)
        ) {
            Icon.Simple(
                modifier = Modifier.align(Alignment.End),
                style = style.styleIconInfo(color = data.iconInfoColor),
                resourceId = data.iconInfoId,
                description = data.title,
            )
            Text.StateColor(
                text = data.title,
                style = style.outfitTextTitle,
                overflow = TextOverflow.Visible
            )
            data.subtitle?.let {
                Text.StateColor(
                    text = it,
                    style = style.outfitTextSubtitle,
                    overflow = TextOverflow.Visible
                )
            }
        }

    }

    @Composable
    private fun ContentIconTop(
        style: Style,
        modifier: Modifier,
        data: Data,
        onClick: () -> Unit
    ) {
        Column(
            modifier = modifier
                .background(style.outfitFrame)
                .clickable {
                    onClick()
                }
                .padding(
                    vertical = MaterialTheme.dimensionsPaddingExtended.block.big.vertical,
                    horizontal = MaterialTheme.dimensionsPaddingExtended.block.normal.horizontal
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon.Simple(
                style = style.styleIconInfo(color = data.iconInfoColor),
                resourceId = data.iconInfoId,
                description = data.title,
            )
            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ){
                Text.StateColor(
                    modifier = Modifier.fillMaxWidth(),
                    text = data.title,
                    style = style.outfitTextTitle?.copy{
                        typo = typo.copy(textAlign = TextAlign.Center)
                    },
                    overflow = TextOverflow.Visible
                )
                data.subtitle?.let {
                    Text.StateColor(
                        text = it,
                        style = style.outfitTextSubtitle,
                        overflow = TextOverflow.Visible
                    )
                }
            }

        }

    }

    @Composable
    private fun ContentIconEnd(
        style: Style,
        modifier: Modifier,
        data: Data,
        onClick: () -> Unit
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(style.outfitFrame)
                .clickable { onClick() }
                .padding(MaterialTheme.dimensionsPaddingExtended.element.normal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text.StateColor(
                    text = data.title,
                    style = style.outfitTextTitle
                )
                data.subtitle?.let {
                    Text.StateColor(
                        text = it,
                        style = style.outfitTextSubtitle
                    )
                }
            }
            Icon.Simple(
                style = style.styleIconInfo(color = data.iconInfoColor),
                resourceId = data.iconInfoId,
                description = data.title,
            )
        }
    }


}