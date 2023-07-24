

package com.tezov.bank.ui.bottomsheet.account.accountIncoming

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tezov.bank.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiModal
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Icon
import com.tezov.lib_core_android_kotlin.ui.component.chunk.Text
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.bottomSheet.BottomSheet
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsCommonExtended
import com.tezov.lib_core_android_kotlin.ui.theme.theme.dimensionsPaddingExtended

object BottomSheetAccountIncoming :
    BottomSheet<BottomSheetAccountIncomingState, BottomSheetAccountIncomingAction> {

    @Composable
    override fun BottomSheet<BottomSheetAccountIncomingState, BottomSheetAccountIncomingAction>.content() {
        val accessor = DiAccessorAppUiModal().with(key = this).contextAccountIncoming().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()

        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                BottomSheetAccountIncomingTheme provides BottomSheetAccountIncomingTheme.provideColors(),
            ),
            parent = {
                arrayOf(
                    BottomSheetAccountIncomingTheme provides BottomSheetAccountIncomingTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    BottomSheetAccountIncomingTheme provides BottomSheetAccountIncomingTheme.provideStyles(),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .background(BottomSheetAccountIncomingTheme.colors.background)
                    .fillMaxWidth()
            ) {
                contentHeader(action = action, title = state.contentData.title)
                Divider(
                    modifier = Modifier.padding(vertical = MaterialTheme.dimensionsPaddingExtended.element.huge.vertical),
                    thickness = MaterialTheme.dimensionsCommonExtended.divider.huge,
                    color = BottomSheetAccountIncomingTheme.colors.fade
                )
                contentBody(
                    body = state.contentData.body,
                    footer = state.contentData.footer
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.contentHeader(
        action: BottomSheetAccountIncomingAction,
        title: String
    ) {
        Row(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimensionsPaddingExtended.page.big.horizontal)
        ) {
            Text.StateColor(
                modifier = Modifier.padding(vertical = MaterialTheme.dimensionsPaddingExtended.page.big.vertical),
                text = title,
                style = BottomSheetAccountIncomingTheme.typographies.title
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Icon.Clickable(
                modifier = Modifier.padding(vertical = MaterialTheme.dimensionsPaddingExtended.page.big.horizontal),
                radiusRipple = BottomSheetAccountIncomingTheme.styles.iconClose.size?.radiusMin,
                colorRipple = BottomSheetAccountIncomingTheme.styles.iconClose.tint,
                onClick = action::onClickClose
            ) {
                Icon.StateColor(
                    style = BottomSheetAccountIncomingTheme.styles.iconClose,
                    resourceId = R.drawable.ic_close_24dp,
                    description = stringResource(id = R.string.pg_a_bts_icon_close),
                )
            }
        }

    }

    @Composable
    private fun ColumnScope.contentBody(
        body: String,
        footer: String,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimensionsPaddingExtended.page.big.horizontal)
                .padding(bottom = MaterialTheme.dimensionsPaddingExtended.page.big.vertical)
        ){
            Text.StateColor(
                text = body,
                style = BottomSheetAccountIncomingTheme.typographies.body
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.normal.vertical))
            Text.StateColor(
                text = footer,
                style = BottomSheetAccountIncomingTheme.typographies.footer
            )
        }
    }


}