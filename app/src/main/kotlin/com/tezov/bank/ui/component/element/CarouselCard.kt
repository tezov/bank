

package com.tezov.bank.ui.component.element

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Icon.Simple.Style.Companion.copy
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitFrameStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.style.OutfitTextStateColor
import com.tezov.lib_adr_sdk_core.ui.theme.style.background
import com.tezov.lib_adr_sdk_core.ui.theme.style.padding
import com.tezov.lib_adr_sdk_core.ui.theme.theme.ThemeColorsExtended
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended
import com.tezov.lib_kmm_core.delegate.DelegateNullFallBack
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Button as ButtonImport

object CarouselCard {

    object Style {

        open class Base(
            margin: PaddingValues? = null,
            outfitFrame: OutfitFrameStateColor? = null,
            styleIconInfo: Icon.Simple.Style? = null,
            val outfitTextTag: OutfitTextStateColor? = null,
            val outfitTextTitle: OutfitTextStateColor? = null,
            val outfitTextBody: OutfitTextStateColor? = null,
            outfitFrameTag: OutfitFrameStateColor? = null,
        ) {
            val margin: PaddingValues by DelegateNullFallBack.Ref(
                margin,
                fallBackValue = {
                    PaddingValues(horizontal = 4.dp)
                }
            )
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
            val outfitFrameTag: OutfitFrameStateColor by DelegateNullFallBack.Ref(
                outfitFrameTag,
                fallBackValue = {
                    ThemeColorsExtended.Dummy.outfitFrameState
                }
            )

            companion object {

                open class Builder internal constructor(
                    style: Base
                ) {
                    var margin = style.margin
                    var outfitFrame = style.outfitFrame
                    var styleIconInfo = style.styleIconInfo
                    var outfitTextTag = style.outfitTextTag
                    var outfitTextTitle = style.outfitTextTitle
                    var outfitTextBody = style.outfitTextBody
                    var outfitFrameTag = style.outfitFrameTag


                    internal open fun get() = Base(
                        margin = margin,
                        outfitFrame = outfitFrame,
                        styleIconInfo = styleIconInfo,
                        outfitTextTag = outfitTextTag,
                        outfitTextTitle = outfitTextTitle,
                        outfitTextBody = outfitTextBody,
                        outfitFrameTag = outfitFrameTag,
                    )
                }

                @Composable
                fun Base.copy(
                    builder: @Composable Builder.() -> Unit = {}
                ) = Builder(this).also {
                    it.builder()
                }.get()

            }

            constructor(style: Base?) : this(
                margin = style?.margin,
                outfitFrame = style?.outfitFrame,
                styleIconInfo = style?.styleIconInfo,
                outfitTextTag = style?.outfitTextTag,
                outfitTextTitle = style?.outfitTextTitle,
                outfitTextBody = style?.outfitTextBody,
                outfitFrameTag = style?.outfitFrameTag,
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

        class Button(
            baseStyle: Base? = null,
            actionStyle: ButtonImport.StateColor.Style? = null,
        ) : Base(baseStyle) {

            val actionStyle: ButtonImport.StateColor.Style by DelegateNullFallBack.Ref(
                actionStyle,
                fallBackValue = { ButtonImport.StateColor.Style() }
            )

            companion object {

                class Builder internal constructor(
                    style: Button
                ) : Base.Companion.Builder(style) {
                    var actionStyle = style.actionStyle

                    override fun get() = Button(
                        baseStyle = super.get(),
                        actionStyle = actionStyle,
                    )
                }

                @Composable
                fun Button.copy(
                    builder: @Composable Builder.() -> Unit = {}
                ) = Builder(this).also {
                    it.builder()
                }.get()

                @Composable
                fun Base.copyToButtonStyle(scope: @Composable Builder.() -> Unit) =
                    Builder(
                        Button(this)
                    ).also { it.scope() }.get()

            }

            constructor(style: Button) : this(
                baseStyle = style,
                actionStyle = style.actionStyle,
            )

        }

        class Link(
            baseStyle: Base? = null,
            actionStyle: OutfitTextStateColor? = null,
        ) : Base(baseStyle) {

            val actionStyle: OutfitTextStateColor by DelegateNullFallBack.Ref(
                actionStyle,
                fallBackValue = { OutfitTextStateColor() }
            )

            companion object {

                class Builder internal constructor(
                    style: Link
                ) : Base.Companion.Builder(style) {
                    var actionStyle = style.actionStyle

                    override fun get() = Link(
                        baseStyle = super.get(),
                        actionStyle = actionStyle,
                    )
                }

                @Composable
                fun Link.copy(
                    builder: @Composable Builder.() -> Unit = {}
                ) = Builder(this).also {
                    it.builder()
                }.get()

                @Composable
                fun Base.copyToLinkStyle(scope: @Composable Builder.() -> Unit) =
                    Builder(
                        Link(this)
                    ).also { it.scope() }.get()

            }

            constructor(style: Link) : this(
                baseStyle = style,
                actionStyle = style.actionStyle,
            )

        }

    }

    data class Data(
        val iconInfoId: Int? = null,
        val iconInfoColor: Color? = null,
        val tag: String? = null,
        val title: String,
        val body: String,
        val action: String,
    )

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style.Base,
        data: Data,
        onClick: () -> Unit = {}
    ) {

        when (style) {
            is Style.Button -> {
                ContentButton(style, modifier, data, onClick)
            }
            is Style.Link -> {
                ContentLink(style, modifier, data, onClick)
            }
        }

    }

    @Composable
    private fun ContentButton(
        style: Style.Button,
        modifier: Modifier,
        data: Data,
        onClick: () -> Unit
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(style.outfitFrame)
                .padding(
                    top = MaterialTheme.dimensionsPaddingExtended.block.big.vertical,
                    start = MaterialTheme.dimensionsPaddingExtended.block.big.horizontal,
                    end = MaterialTheme.dimensionsPaddingExtended.block.big.horizontal,
                    bottom = MaterialTheme.dimensionsPaddingExtended.block.normal.vertical,
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                data.tag?.let {
                    Column(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                            .background(style.outfitFrameTag)
                    ) {
                        Text.StateColor(
                            modifier = Modifier.padding(
                                vertical = MaterialTheme.dimensionsPaddingExtended.element.small.vertical,
                                horizontal = MaterialTheme.dimensionsPaddingExtended.element.big.horizontal
                            ),
                            text = it,
                            style = style.outfitTextTag,
                        )
                    }
                }
                Text.StateColor(
                    modifier = Modifier.padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.big.vertical),
                    text = data.title,
                    style = style.outfitTextTitle,
                    overflow = TextOverflow.Ellipsis
                )
                Text.StateColor(
                    modifier = Modifier.padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical),
                    text = data.body,
                    style = style.outfitTextBody,
                )
                Spacer(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f)
                )
                ButtonImport.StateColor(
                    text = data.action,
                    style = style.actionStyle,
                    onClick = onClick,
                )
            }
            data.iconInfoId?.let {
                Icon.Simple(
                    modifier = Modifier.align(Alignment.Top),
                    resourceId = it,
                    description = null,
                    style = style.styleIconInfo(data.iconInfoColor)
                )
            }
        }
    }

    @Composable
    private fun ContentLink(
        style: Style.Link,
        modifier: Modifier,
        data: Data,
        onClick: () -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(style.outfitFrame)
                .padding(MaterialTheme.dimensionsPaddingExtended.block.big)
        ) {
            data.tag?.let {
                Column(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                        .background(style.outfitFrameTag)
                ) {
                    Text.StateColor(
                        modifier = Modifier.padding(
                            vertical = MaterialTheme.dimensionsPaddingExtended.element.small.vertical,
                            horizontal = MaterialTheme.dimensionsPaddingExtended.element.big.horizontal
                        ),
                        text = it,
                        style = style.outfitTextTag,
                    )
                }
            }
            Row {
                data.iconInfoId?.let {
                    Icon.Simple(
                        modifier = Modifier.padding(end = MaterialTheme.dimensionsPaddingExtended.element.normal.horizontal),
                        resourceId = it,
                        description = null,
                        style = style.styleIconInfo(data.iconInfoColor)
                    )
                }
                Text.StateColor(
                    modifier = Modifier.padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.big.vertical),
                    text = data.title,
                    style = style.outfitTextTitle,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text.StateColor(
                modifier = Modifier.padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.big.vertical),
                text = data.body,
                style = style.outfitTextBody,
            )
            Spacer(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            )
            Text.Clickable.invoke(
                onClick = onClick
            ) {
                Text.StateColor(
                    text = data.action,
                    style = style.actionStyle
                )
            }
        }
    }

}




