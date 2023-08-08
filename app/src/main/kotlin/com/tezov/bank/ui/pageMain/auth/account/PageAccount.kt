

package com.tezov.bank.ui.pageMain.auth.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tezov.app.R
import com.tezov.bank.ui.component.block.SectionAccountValueSimpleRow
import com.tezov.bank.ui.component.element.AccountSummaryCard
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController
import com.tezov.lib_adr_app_core.navigation.navigator.GraphEntry
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Icon
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Shadow
import com.tezov.lib_adr_ui_cpt.core.component.chunk.Text
import com.tezov.lib_adr_ui_cpt.core.component.layout.ColumnCollapsibleHeader
import com.tezov.lib_adr_app_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_ui_core.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_ui_core.modifier.then
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsCommonExtended
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended

object PageAccount : Page<PageAccountState, PageAccountAction> {

    private const val DIVIDER_HEADER_VISIBILITY_START = 0.3f
    private const val ICON_ACTION_SCALE_MIN = 0.85f
    private const val ICON_ACTION_Y_OFFSET_FACTOR = 0.15f

    @Composable
    override fun Page<PageAccountState, PageAccountAction>.content(graphEntry: GraphEntry, innerPadding: PaddingValues) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextAccount().apply {
            remember()
        }
        val action = accessor.action()
        val state = accessor.state()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageAccountTheme provides PageAccountTheme.provideColors(),
                PageAccountTheme provides PageAccountTheme.provideDimensions(),
            ),
            parent = {
                arrayOf(
                    PageAccountTheme provides PageAccountTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageAccountTheme provides PageAccountTheme.provideStyles(),
                )
            }
        ) {
            ColumnCollapsibleHeader(
                modifier = Modifier
                    .background(PageAccountTheme.colors.background)
                    .padding(innerPadding),
                properties = PageAccountTheme.dimensions.headerProperties,
                header = { progress, progressDp ->
                    contentHeader(
                        action = action,
                        header = state.header,
                        properties = PageAccountTheme.dimensions.headerProperties,
                        progress = progress,
                        progressDp = progressDp
                    )
                },
                body = {
                    contentBody(
                        action = action,
                        incoming = state.incomings,
                        histories = state.histories
                    )
                }
            )
        }
    }


    @Composable
    private fun BoxScope.contentHeader(
        action: PageAccountAction,
        header: PageAccountState.Header?,
        properties: ColumnCollapsibleHeader.Properties,
        progress: Float,
        progressDp: Dp,
    ) {
        if (header == null) {
            return
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(progressDp)
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(properties.max - PageAccountTheme.dimensions.spacingBottomHeaderBackground),
                    painter = painterResource(id = R.drawable.bg_account),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(PageAccountTheme.dimensions.spacingBottomHeaderBackground))
            }
            Row(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical,
                        bottom = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical,
                        start = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal,
                        end = MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal,
                    )
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.0f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    header.headline?.let {
                        Text.StateColor(
                            modifier = Modifier
                                .weight(1.0f)
                                .padding(bottom = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical)
                                .offset(0.dp, progressDp - properties.max),
                            text = it,
                            style = PageAccountTheme.typographies.headline.copy {
                                outfitState = PageAccountTheme.colors.background.asStateSimple
                            }
                        )
                    } ?: run {
                        Spacer(modifier = Modifier.weight(1.0f))
                    }
                    header.accountSummary?.let {
                        Row {
                            AccountSummaryCard(
                                progress = progress,
                                modifier = Modifier.fillMaxWidth(),
                                style = PageAccountTheme.styles.accountSummary,
                                data = it,
                                onClick = action::onClickAccountSummary
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .offset(
                            (MaterialTheme.dimensionsPaddingExtended.page.normal.horizontal / 2) * (1f - progress),
                            ((properties.max - progressDp) * ICON_ACTION_Y_OFFSET_FACTOR)
                        )
                        .scale(progress.coerceAtLeast(ICON_ACTION_SCALE_MIN))
                ) {
                    header.iconActionMessageId?.let {
                        Icon.Clickable(
                            modifier = Modifier
                                .padding(end = MaterialTheme.dimensionsPaddingExtended.element.small.horizontal),
                            radiusRipple = PageAccountTheme.styles.icon.size?.radiusMin,
                            colorRipple = PageAccountTheme.styles.icon.tint,
                            onClick = action::onClickMessageInfo
                        ) {
                            Icon.StateColor(
                                style = PageAccountTheme.styles.icon,
                                resourceId = it,
                                description = null
                            )
                        }
                    }
                    header.iconActionAccountId?.let {
                        Icon.Clickable(
                            radiusRipple = PageAccountTheme.styles.icon.size?.radiusMin,
                            colorRipple = PageAccountTheme.styles.icon.tint,
                            onClick = action::onClickAccount
                        ) {
                            Icon.StateColor(
                                style = PageAccountTheme.styles.icon,
                                resourceId = it,
                                description = null
                            )
                        }
                    }
                }
            }
            Shadow.Line(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .then(progress < DIVIDER_HEADER_VISIBILITY_START,
                        onTrue = {
                            alpha((DIVIDER_HEADER_VISIBILITY_START - progress) / DIVIDER_HEADER_VISIBILITY_START)
                        },
                        onFalse = {
                            alpha(0f)
                        }
                    ),
                elevation = (MaterialTheme.dimensionsCommonExtended.elevation.normal * (1 - progress)),
            )
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        action: PageAccountAction,
        incoming: SectionAccountValueSimpleRow.Data?,
        histories: List<SectionAccountValueSimpleRow.Data>?
    ) {
        incoming?.let { data ->
            SectionAccountValueSimpleRow(
                modifier = Modifier.padding(start = MaterialTheme.dimensionsPaddingExtended.page.small.horizontal),
                data = data,
                style = PageAccountTheme.styles.sectionAccountValue,
                onClickInfo = action::onClickIncomingHelp,
                onClickRow = {
                    action.onClickAccountHistories(-1, it)
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
        }
        histories?.let { list ->
            list.forEachIndexed { index, data ->
                SectionAccountValueSimpleRow(
                    modifier = Modifier.padding(start = MaterialTheme.dimensionsPaddingExtended.page.small.horizontal),
                    data = data,
                    style = PageAccountTheme.styles.sectionAccountValue,
                    onClickRow = {
                        action.onClickAccountHistories(index, it)
                    }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
            }
        }
        Spacer(modifier = Modifier.defaultMinSize(minHeight = MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
    }

    @Composable
    override fun onBackButtonPressed() = DialogCloseAppController.handleOnBackPressed()

}