

package com.tezov.bank.ui.pageMain.lobby.lounge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.tezov.app.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.lib_core_android_kotlin.animation.AnimationCompound.Companion.animate
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.paintArt.Shape
import com.tezov.lib_core_android_kotlin.ui.component.block.HorizontalPager
import com.tezov.lib_core_android_kotlin.ui.component.chunk.*
import com.tezov.lib_core_android_kotlin.ui.compositionTree.page.Page
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_core_android_kotlin.ui.theme.style.OutfitText.StateColor.Style.Companion.copy
import com.tezov.lib_core_android_kotlin.ui.theme.style.padding
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsPaddingExtended

object PageLounge : Page<PageLoungeState, PageLoungeAction> {

    @Composable
    override fun Page<PageLoungeState, PageLoungeAction>.content(
        graphEntry: GraphEntry,
        innerPadding: PaddingValues
    ) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextLounge().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()
        state.animation.updateTransition()
        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageLoungeTheme provides PageLoungeTheme.provideColors(),
                PageLoungeTheme provides PageLoungeTheme.provideDimensions(),
            ),
            parent = {
                arrayOf(
                    PageLoungeTheme provides PageLoungeTheme.provideShapes(),
                    PageLoungeTheme provides PageLoungeTheme.provideBorders(),
                    PageLoungeTheme provides PageLoungeTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageLoungeTheme provides PageLoungeTheme.provideStyles()
                )
            }
        ) {
            contentLoading(state, innerPadding)
            contentLoaded(state, action, innerPadding)
        }
    }

    @Composable
    private fun contentLoading(
        state: PageLoungeState,
        innerPadding: PaddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PageLoungeTheme.colors.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.0f)
            ) {
                Image.Simple(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .animate(state.animation.logo),
                    style = PageLoungeTheme.styles.logo,
                    resourceId = R.drawable.logo_tezov_bank_inverse,
                    description = stringResource(id = R.string.pg_login_img_logo)
                )
            }
            contentRating(state, state.header)
        }
    }

    @Composable
    private fun ColumnScope.contentRating(
        state: PageLoungeState,
        header: PageLoungeState.Header?
    ) {
        header ?: return
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.0f)
                .animate(state.animation.rating),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header.title?.let {
                Text.StateColor(
                    text = it,
                    style = PageLoungeTheme.typographies.title
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.block.huge.vertical))
            }
            header.headline?.let {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Shape.Star(size = 10.dp, color = Color.White)
                    Shape.Star(size = 15.dp, color = Color.White, formFactor = 0.4f)
                    Shape.Star(size = 10.dp, color = Color.White)
                }
                Text.StateColor(
                    text = it,
                    style = PageLoungeTheme.typographies.headline
                )
            }
            header.body?.let {
                Text.StateColor(
                    text = it,
                    style = PageLoungeTheme.typographies.body
                )
            }
            header.label?.let {
                Text.StateColor(
                    text = it,
                    style = PageLoungeTheme.typographies.label
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            )
            header.note?.let {
                Text.StateColor(
                    text = it,
                    style = PageLoungeTheme.typographies.note
                )
            }
        }
    }

    @Composable
    private fun contentLoaded(
        state: PageLoungeState,
        action: PageLoungeAction,
        innerPadding: PaddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = MaterialTheme.dimensionsPaddingExtended.page.huge.vertical)
                .animate(state.animation.page)
        ) {
            contentHeader(
                action = action,
                iconState = state.iconState,
            )
            contentBody(
                action = action,
                nameState = state.nameState
            )
            contentFooter(
                action = action,
            )
        }
    }

    @Composable
    private fun ColumnScope.contentHeader(
        action: PageLoungeAction,
        iconState: State<Int>,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimensionsPaddingExtended.page.huge.horizontal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Image.StateColor(
                style = PageLoungeTheme.styles.iconBig,
                resourceId = iconState.value,
                description = stringResource(id = R.string.pg_login_img_suit_case)
            )
            Spacer(modifier = Modifier.width(PageLoungeTheme.dimensions.paddingStartToIconBig))
            Icon.Clickable(
                radiusRipple = PageLoungeTheme.styles.iconMedium.size?.radiusMin,
                colorRipple = PageLoungeTheme.styles.iconMedium.tint,
                onClick = action::onClickAdd
            ) {
                Icon.StateColor(
                    resourceId = R.drawable.ic_add_24dp,
                    style = PageLoungeTheme.styles.iconMedium,
                    description = stringResource(id = R.string.pg_login_icon_add_account)
                )
            }
            Spacer(modifier = Modifier.width(PageLoungeTheme.dimensions.paddingStartToIconMedium))
            DropDownMenu.StateColor(
                style = PageLoungeTheme.styles.dropDownMenu,
                resourceId = R.drawable.ic_3dot_v_24dp,
                description = stringResource(id = R.string.pg_login_icon_more_action),
                items = stringArrayResource(id = R.array.pg_login_drop_down_menu).toList(),
                offset = DpOffset(
                    PageLoungeTheme.dimensions.iconSmall.width / 2,
                    -PageLoungeTheme.dimensions.iconSmall.height / 5
                ),
                onClick = action::onClickMenu
            )
        }
    }

    @Composable
    private fun ColumnScope.contentBody(
        action: PageLoungeAction,
        nameState: State<String>
    ) {
        HorizontalPager.WithIndicator(
            modifier = Modifier
                .weight(1f),
            style = PageLoungeTheme.styles.pager,
            itemSelected = 0,
            items = arrayListOf(
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text.StateColor(
                            text = nameState.value,
                            style = PageLoungeTheme.typographies.supra
                        )
                        Spacer(modifier = Modifier.height(PageLoungeTheme.dimensions.spacingTopToTitle))
                        Text.StateColor(
                            text = stringResource(id = R.string.pg_login_pager_0),
                            style = PageLoungeTheme.typographies.body.copy {
                                typo = typo.copy(textAlign = TextAlign.Center)
                            }
                        )
                    }
                },
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text.StateColor(
                            text = stringResource(id = R.string.pg_login_pager_1),
                            style = PageLoungeTheme.typographies.huge.copy {
                                typo = typo.copy(textAlign = TextAlign.Center)
                            }
                        )
                        Spacer(modifier = Modifier.height(PageLoungeTheme.dimensions.spacingTopToTitle))
                        Button.StateColor(
                            modifierText = Modifier
                                .padding(MaterialTheme.dimensionsPaddingExtended.text.big),
                            onClick = action::onClickBalanceActivate,
                            text = stringResource(id = R.string.pg_login_btn_activate_balance),
                            style = PageLoungeTheme.styles.buttonOutlined
                        )
                    }
                })
        )
    }

    @Composable
    private fun ColumnScope.contentFooter(
        action: PageLoungeAction,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimensionsPaddingExtended.page.huge.horizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button.StateColor(
                modifierButton = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimensionsPaddingExtended.block.normal.vertical),
                modifierText = Modifier
                    .padding(MaterialTheme.dimensionsPaddingExtended.text.big),
                text = stringResource(id = R.string.pg_login_btn_connect),
                style = PageLoungeTheme.styles.buttonDark,
                onClick = action::onClickConnect,
            )
            Button.StateColor(
                modifierButton = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimensionsPaddingExtended.element.normal.vertical),
                modifierText = Modifier
                    .padding(MaterialTheme.dimensionsPaddingExtended.text.big),
                text = stringResource(id = R.string.pg_login_btn_send_money),
                style = PageLoungeTheme.styles.buttonLight,
                onClick = action::onClickSendMoney,
            )
            Spacer(modifier = Modifier.height(PageLoungeTheme.dimensions.spacingTopFromLinkService))
            Text.Clickable.invoke(
                onClick = action::onClickHelpAndService,
            ) {
                Text.StateColor(
                    text = stringResource(id = R.string.pg_login_link_help_and_service),
                    style = PageLoungeTheme.styles.link,
                )
            }
        }
    }

}