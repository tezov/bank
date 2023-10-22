package com.tezov.bank.ui.di.module


import com.tezov.bank.ui.di.annotation.scope.ScopeAppUiModal
import com.tezov.lib_adr_app_core.ui.di.common.ComposableContext
import com.tezov.lib_adr_app_core.ui.di.common.ComposableWrapper
import com.tezov.lib_adr_app_core.ui.di.module.ModuleCoreUiActivity
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface ModuleAppUiModal {

    @Module
    class MapperContext {

        @ScopeAppUiModal
        @Provides
        fun provideContextCloseAppConfirmation(
            state: State.DialogCloseAppConfirmationState,
            action: Action.DialogCloseAppConfirmationAction
        ) = ComposableContext(state, action)

        @ScopeAppUiModal
        @Provides
        fun provideContextLoginForgotten(
            state: State.DialogLoginForgottenState,
            action: Action.DialogLoginForgottenAction
        ) = ComposableContext(state, action)

        @ScopeAppUiModal
        @Provides
        fun provideContextAccountIncoming(
            state: State.BottomSheetAccountIncomingState,
            action: Action.BottomSheetAccountIncomingAction
        ) = ComposableContext(state, action)

    }

    object State {

        @ScopeAppUiModal
        class DialogCloseAppConfirmationState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmationState>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmationState.create()
        }

        @ScopeAppUiModal
        class DialogLoginForgottenState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgottenState>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgottenState.create()
        }

        @ScopeAppUiModal
        class BottomSheetAccountIncomingState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncomingState>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncomingState.create()
        }

    }

    object Action {

        @ScopeAppUiModal
        class DialogCloseAppConfirmationAction @Inject constructor(
            private val action: ModuleCoreUiActivity.Action.DialogAction,
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmationAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmationAction.create(
                    action = action.get(),
                    navigationController = navigationController.get()
                )
        }

        @ScopeAppUiModal
        class DialogLoginForgottenAction @Inject constructor(
            private val action: ModuleCoreUiActivity.Action.DialogAction,
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgottenAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgottenAction.create(
                    action = action.get(),
                    navigationController = navigationController.get()
                )
        }

        @ScopeAppUiModal
        class BottomSheetAccountIncomingAction @Inject constructor(
            private val action: ModuleCoreUiActivity.Action.BottomSheetAction,
        ) : ComposableWrapper<com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncomingAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncomingAction.create(
                    action = action.get(),
                )
        }

    }

}