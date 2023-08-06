

package com.tezov.bank.ui.dialog.auth.closeAppConfirmation

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiModal
import com.tezov.lib_adr_sdk_core.ui.component.chunk.Text
import com.tezov.lib_adr_sdk_core.ui.compositionTree.modal.dialog.Dialog
import com.tezov.lib_adr_sdk_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_sdk_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_sdk_core.ui.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_sdk_core.ui.theme.style.padding
import com.tezov.lib_adr_sdk_core.ui.theme.theme.dimensionsPaddingExtended

object DialogCloseAppConfirmation :
    Dialog<DialogCloseAppConfirmationState, DialogCloseAppConfirmationAction> {

    @Composable
    override fun Dialog<DialogCloseAppConfirmationState, DialogCloseAppConfirmationAction>.content() {
        val accessor = DiAccessorAppUiModal().with(key = this).contextCloseAppConfirmation().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()

        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                DialogCloseAppConfirmationTheme provides DialogCloseAppConfirmationTheme.provideColors(),
            ),
            parent = {
                arrayOf(
                    DialogCloseAppConfirmationTheme provides DialogCloseAppConfirmationTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    DialogCloseAppConfirmationTheme provides DialogCloseAppConfirmationTheme.provideStyles()
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(MaterialTheme.dimensionsPaddingExtended.page.normal)
            ) {
                Text.StateColor(
                    text = state.contentData.title,
                    style = DialogCloseAppConfirmationTheme.typographies.title
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.normal.vertical))
                Text.StateColor(
                    text = state.contentData.body,
                    style = DialogCloseAppConfirmationTheme.typographies.body
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.huge.vertical))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text.Clickable.invoke(
                        onClick = action::onClickCancel
                    ) {
                        Text.StateColor(
                            text = state.contentData.cancel,
                            style = DialogCloseAppConfirmationTheme.styles.linkCancel,
                        )
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.dimensionsPaddingExtended.element.huge.horizontal))
                    Text.Clickable.invoke(
                        onClick = action::onClickConfirm
                    ) {
                        Text.StateColor(
                            text = state.contentData.confirm,
                            style = DialogCloseAppConfirmationTheme.styles.linkConfirm,
                        )
                    }
                }
            }
        }
    }
}
