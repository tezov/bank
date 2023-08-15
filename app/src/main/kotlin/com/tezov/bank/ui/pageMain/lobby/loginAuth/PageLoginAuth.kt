

package com.tezov.bank.ui.pageMain.lobby.loginAuth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.tezov.app.R
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiPage
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState.Companion.LOGIN_LENGTH
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState.Companion.PASSWORD_LENGTH
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState.Field
import com.tezov.lib_adr_app_core.navigation.navigator.GraphEntry
import com.tezov.lib_adr_app_core.ui.compositionTree.page.Page
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_ui_cpt.component.core.block.KeyBoard
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Button
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Icon
import com.tezov.lib_adr_ui_cpt.component.core.chunk.Text
import com.tezov.lib_adr_ui_cpt.component.core.chunk.TextField
import com.tezov.lib_adr_ui_core.extension.ExtensionCompositionLocal
import com.tezov.lib_adr_ui_core.localProvider.LocalProviderTextField
import com.tezov.lib_adr_ui_core.theme.style.OutfitBorder.StateColor.Style.Companion.copy
import com.tezov.lib_adr_ui_core.theme.style.OutfitState.Simple.Style.Companion.asStateSimple
import com.tezov.lib_adr_ui_core.theme.style.border
import com.tezov.lib_adr_ui_core.theme.style.padding
import com.tezov.lib_adr_ui_core.theme.theme.*
import com.tezov.lib_adr_ui_core.type.primaire.size
import com.tezov.lib_kmm_core.type.collection.ListEntry

object PageLoginAuth : Page<PageLoginAuthState, PageLoginAuthAction> {

    @Composable
    override fun Page<PageLoginAuthState, PageLoginAuthAction>.content(
        graphEntry: GraphEntry,
        innerPadding: PaddingValues
    ) {
        val accessor = DiAccessorAppUiPage().with(key = this).contextLoginAuth().apply {
            remember()
        }
        val state = accessor.state()
        val action = accessor.action()

        ExtensionCompositionLocal.CompositionLocalProvider(
            ancestor = arrayOf(
                PageLoginAuthTheme provides PageLoginAuthTheme.provideColors(),
            ),
            parent = {
                arrayOf(
                    PageLoginAuthTheme provides PageLoginAuthTheme.provideTypographies(),
                )
            },
            child = {
                arrayOf(
                    PageLoginAuthTheme provides PageLoginAuthTheme.provideStyles(),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PageLoginAuthTheme.colors.background)
            ) {
                Icon.Clickable(
                    radiusRipple = MaterialTheme.dimensionsIconExtended.modal.normal.radiusMin,
                    colorRipple = PageLoginAuthTheme.colors.onBackground,
                    onClick = action::onClickClose
                ) {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimensionsIconExtended.modal.normal),
                        painter = painterResource(id = com.tezov.lib_adr_ui_cpt.R.drawable.ic_close_24dp),
                        contentDescription = stringResource(id = R.string.dlg_login_auth_icon_close),
                        tint = PageLoginAuthTheme.colors.onBackground,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.dimensionsPaddingExtended.page.big),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    contentHeader()
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
                    contentBody(state.textFieldValues)
                    Spacer(modifier = Modifier.height(MaterialTheme.dimensionsPaddingExtended.element.big.vertical))
                    contentFooter(
                        action = action,
                        credentialValidState = state.credentialValidState
                    )
                }
            }
        }
    }

    @Composable
    private fun ColumnScope.contentHeader() {
        Text.StateColor(
            text = R.string.dlg_login_auth_enter_password,
            style = PageLoginAuthTheme.typographies.title
        )
    }

    //TODO modifier lenght control voir todo de PageLoginAuthFormManager
    @Composable
    private fun ColumnScope.contentBody(
        textFieldValues: ListEntry<Field, TextField.State>,
    ) {
        CompositionLocalProvider(
            LocalProviderTextField.textSelectionColorsNoColor,
            LocalProviderTextField.textSelectionToolbarDisabled
        ) {
            val formManager = remember {
                PageLoginAuthFormManager(textFieldValues = textFieldValues)
            }
            formManager.compose()

            val login = remember {
                textFieldValues.getValue(Field.Login)
            }
            val password = remember {
                textFieldValues.getValue(Field.Password)
            }

            if (login == null || password == null) {
                throw IllegalStateException("can't be null")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(MaterialTheme.bordersExtended.block.normal.copy {
                        outfitState = PageLoginAuthTheme.colors.onBackground.asStateSimple
                    }, MaterialTheme.shapesExtended.block.small)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField.StateColor(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimensionsPaddingExtended.chunk.normal.horizontal)
                            .padding(bottom = MaterialTheme.dimensionsPaddingExtended.chunk.small.vertical),
                        state = login,
                        style = PageLoginAuthTheme.styles.field,
                        singleLine = true,
                        label = stringResource(id = R.string.dlg_login_auth_fld_label_login),
                        placeholder = stringResource(id = R.string.dlg_login_auth_fld_placeholder_login),
                        painterIconStart = painterResource(id = R.drawable.ic_person_24dp),
                        descriptionIconStart = stringResource(id = R.string.dlg_login_auth_icon_login_clear),
                        painterIconEnd = when (login.current.isEmpty()) {
                            false -> painterResource(id = com.tezov.lib_adr_ui_cpt.R.drawable.ic_cancel_circle_24dp)
                            true -> null
                        },
                        descriptionIconEnd = stringResource(id = R.string.dlg_login_auth_icon_login_clear),
                        onClickIconEnd = {
                            login.current = ""
                            formManager.requestFocus(login)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = if (password.current.length < PASSWORD_LENGTH) ImeAction.Next
                            else ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                formManager.requestClearFocus()
                            },
                            onNext = {
                                formManager.requestFocus(password)
                            }
                        ),
                        acceptChange = {
                            it.next.length <= LOGIN_LENGTH
                        },
                        onChanged = {
                            formManager.onChange(Field.Login)
                        }
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimensionsCommonExtended.divider.normal),
                    color = PageLoginAuthTheme.colors.fade
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CompositionLocalProvider(
                        LocalProviderTextField.keyboardDisabled
                    ) {
                        TextField.StateColor(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialTheme.dimensionsPaddingExtended.chunk.normal.horizontal)
                                .padding(bottom = MaterialTheme.dimensionsPaddingExtended.chunk.small.vertical),
                            state = password,
                            style = PageLoginAuthTheme.styles.field,
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            label = stringResource(id = R.string.dlg_login_auth_fld_label_password),
                            placeholder = stringResource(id = R.string.dlg_login_auth_fld_placeholder_password),
                            painterIconStart = painterResource(id = R.drawable.ic_lock_24dp),
                            descriptionIconStart = stringResource(id = R.string.dlg_login_auth_icon_password_info),
                            painterIconEnd = when (password.current.isEmpty()) {
                                false -> painterResource(id = R.drawable.ic_backspace_24dp)
                                true -> null
                            },
                            descriptionIconEnd = stringResource(id = R.string.dlg_login_auth_icon_password_delete),
                            onClickIconEnd = {
                                val current = password.current
                                current.takeIf { it.isNotEmpty() }?.let {
                                    password.current = it.dropLast(1)
                                    formManager.requestFocus(password)
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                            ),
                            keyboardActions = KeyboardActions(),
                            readOnly = true,
                            acceptChange = { false }
                        )
                    }
                }
                KeyBoard.GridCubeDigitsTwoRowShuffled(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(
                            if (!formManager.hasFocus(password)) {
                                0.35f
                            } else if (password.current.length >= PASSWORD_LENGTH) {
                                formManager.requestClearFocus()
                                0.35f
                            } else {
                                1.0f
                            }
                        ),
                    style = PageLoginAuthTheme.styles.keyBoardGridCube
                ) {
                    val current = password.current
                    if (current.length < PASSWORD_LENGTH) {
                        password.current = current + it
                    }
                    formManager.onChange(Field.Password)
                }
            }

            LaunchedEffect(Unit) {
                if (login.current.length < LOGIN_LENGTH) {
                    formManager.requestFocus(login)
                } else if (password.current.length < PASSWORD_LENGTH) {
                    formManager.requestFocus(password)
                }
            }
            DisposableEffect(Unit) {
                onDispose {
                    formManager.hideKeyboard()
                }
            }

        }
    }

    @Composable
    private fun ColumnScope.contentFooter(
        action: PageLoginAuthAction,
        credentialValidState: Boolean,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button.StateColor(
                modifierButton = Modifier
                    .fillMaxWidth(),
                modifierText = Modifier
                    .padding(MaterialTheme.dimensionsPaddingExtended.text.big),
                text = stringResource(id = R.string.dlg_login_auth_btn_connect),
                style = PageLoginAuthTheme.styles.button,
                enabled = credentialValidState,
                onClick = action::onClickConnect,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimensionsPaddingExtended.element.supra.vertical),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text.Clickable.invoke(
                    onClick = action::onClickLoginForgotten,
                ) {
                    Text.StateColor(
                        text = stringResource(id = R.string.dlg_login_auth_link_login_forgotten),
                        style = PageLoginAuthTheme.styles.link,
                    )
                }
                Text.Clickable.invoke(
                    onClick = action::onClickPasswordForgotten,
                ) {
                    Text.StateColor(
                        text = stringResource(id = R.string.dlg_login_auth_link_password_forgotten),
                        style = PageLoginAuthTheme.styles.link,
                    )
                }


            }
        }
    }

}