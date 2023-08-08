

package com.tezov.bank.ui.di.module

import com.tezov.bank.ui.di.annotation.scope.ScopeAppUiPage
import com.tezov.lib_adr_app_core.ui.di.common.ComposableContext
import com.tezov.lib_adr_app_core.ui.di.common.ComposableWrapper
import com.tezov.lib_adr_app_core.ui.di.module.ModuleCoreUiActivity
import dagger.Module
import dagger.Provides
import javax.inject.Inject


interface ModuleAppUiPage {

    @Module
    class MapperContext {

        //--LOBBY-- *******
        @ScopeAppUiPage
        @Provides
        fun provideContextSplash(
            state: State.PageSplashState,
            action: Action.PageSplashAction
        ) = ComposableContext(state, action)


        @ScopeAppUiPage
        @Provides
        fun provideContextLounge(
            state: State.PageLoungeState,
            action: Action.PageLoungeAction
        ) = ComposableContext(state, action)


        @ScopeAppUiPage
        @Provides
        fun provideContextDialogLoginAuth(
            state: State.DialogLoginAuthState,
            action: Action.DialogLoginAuthAction
        ) = ComposableContext(state, action)

        @ScopeAppUiPage
        @Provides
        fun provideContextHelpAndService(
            state: State.PageHelpAndServiceState,
            action: Action.PageHelpAndServiceAction
        ) = ComposableContext(state, action)
        //............ *******

        //--AUTH-- *******
            //--account-- *******
            @ScopeAppUiPage
            @Provides
            fun provideContextAccount(
                state: State.PageAccountState,
                action: Action.PageAccountAction
            ) = ComposableContext(state, action)

            @ScopeAppUiPage
            @Provides
            fun provideContextMessageInfo(
                state: State.PageMessageInfoState,
                action: Action.PageMessageInfoAction
            ) = ComposableContext(state, action)
            //............ *******

        @ScopeAppUiPage
        @Provides
        fun provideContextDiscover(
            state: State.PageDiscoverState,
            action: Action.PageDiscoverAction
        ) = ComposableContext(state, action)

        @ScopeAppUiPage
        @Provides
        fun provideContextHelp(
            state: State.PageHelpState,
            action: Action.PageHelpAction
        ) = ComposableContext(state, action)

        @ScopeAppUiPage
        @Provides
        fun provideContextPayment(
            state: State.PagePaymentState,
            action: Action.PagePaymentAction
        ) = ComposableContext(state, action)

        @ScopeAppUiPage
        @Provides
        fun provideContextProfile(
            state: State.PageProfileState,
            action: Action.PageProfileAction
        ) = ComposableContext(state, action)
        //............ *******

        //--COMMON-- *******
        @ScopeAppUiPage
        @Provides
        fun provideContextWebView(
            state: State.PageWebViewState,
            action: Action.PageWebViewAction
        ) = ComposableContext(state, action)
        //............ *******
    }

    object Provided {

        @ScopeAppUiPage
        class DialogAuthCloseAppController @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
            private val dialogAction: ModuleCoreUiActivity.Action.DialogAction,
        ) : ComposableWrapper<com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppController.create(
                    navigationController.get(),
                    dialogAction.get()
                )
        }

    }

    object State {

        //--LOBBY-- *******
        @ScopeAppUiPage
        class PageSplashState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.splash.PageSplashState>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.splash.PageSplashState.create()
        }

        @ScopeAppUiPage
        class PageLoungeState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeState>() {
            @androidx.compose.runtime.Composable
            override fun create() = com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeState.create()
        }

        @ScopeAppUiPage
        class DialogLoginAuthState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState.create()
        }

        @ScopeAppUiPage
        class PageHelpAndServiceState @Inject constructor(
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceState>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceState.create()
        }
        //............ *******

        //--AUTH-- *******
            //--account-- *******
            @ScopeAppUiPage
            class PageAccountState @Inject constructor() :
                ComposableWrapper<com.tezov.bank.ui.pageMain.auth.account.PageAccountState>() {
                @androidx.compose.runtime.Composable
                override fun create() = com.tezov.bank.ui.pageMain.auth.account.PageAccountState.create()
            }

            @ScopeAppUiPage
            class PageMessageInfoState @Inject constructor() :
                ComposableWrapper<com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfoState>() {
                @androidx.compose.runtime.Composable
                override fun create() = com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfoState.create()
            }
            //............ *******

        @ScopeAppUiPage
        class PageDiscoverState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.auth.discover.PageDiscoverState>() {
            @androidx.compose.runtime.Composable
            override fun create() = com.tezov.bank.ui.pageMain.auth.discover.PageDiscoverState.create()
        }

        @ScopeAppUiPage
        class PageHelpState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.auth.help.PageHelpState>() {
            @androidx.compose.runtime.Composable
            override fun create() = com.tezov.bank.ui.pageMain.auth.help.PageHelpState.create()
        }

        @ScopeAppUiPage
        class PagePaymentState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.auth.payment.PagePaymentState>() {
            @androidx.compose.runtime.Composable
            override fun create() = com.tezov.bank.ui.pageMain.auth.payment.PagePaymentState.create()
        }

        @ScopeAppUiPage
        class PageProfileState @Inject constructor() :
            ComposableWrapper<com.tezov.bank.ui.pageMain.auth.profile.PageProfileState>() {
            @androidx.compose.runtime.Composable
            override fun create() = com.tezov.bank.ui.pageMain.auth.profile.PageProfileState.create()
        }
        //............ *******

        //--AUTH-- *******
        @ScopeAppUiPage
        class PageWebViewState @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageSecondary.common.PageWebViewState>() {
            @androidx.compose.runtime.Composable
            override fun create() = com.tezov.bank.ui.pageSecondary.common.PageWebViewState.create(
                navigationController.get().finalRoute(copyArgument = true) ?: kotlin.run {
                    throw IllegalStateException("Current route not found")
                }
            )
        }
        //............ *******
    }

    object Action {
        //--LOBBY-- *******
        @ScopeAppUiPage
        class PageSplashAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.splash.PageSplashAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.splash.PageSplashAction.create(
                    navigationController.get()
                )
        }

        @ScopeAppUiPage
        class PageLoungeAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAction.create(
                    navigationController.get(),
                )
        }

        @ScopeAppUiPage
        class DialogLoginAuthAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
            private val dialogAction: ModuleCoreUiActivity.Action.DialogAction,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthAction.create(
                    navigationController.get(),
                    dialogAction.get()
                )
        }

        @ScopeAppUiPage
        class PageHelpAndServiceAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceAction.create(
                    navigationController.get(),
                )
        }
        //............ *******

        //--AUTH-- *******
            //--account-- *******
            @ScopeAppUiPage
            class PageAccountAction @Inject constructor(
                private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
                private val bottomSheetAction: ModuleCoreUiActivity.Action.BottomSheetAction,
            ) : ComposableWrapper<com.tezov.bank.ui.pageMain.auth.account.PageAccountAction>() {
                @androidx.compose.runtime.Composable
                override fun create() =
                    com.tezov.bank.ui.pageMain.auth.account.PageAccountAction.create(
                        navigationController.get(),
                        bottomSheetAction.get()
                    )
            }

            @ScopeAppUiPage
            class PageMessageInfoAction @Inject constructor(
                private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
            ) : ComposableWrapper<com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfoAction>() {
                @androidx.compose.runtime.Composable
                override fun create() =
                    com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfoAction.create(
                        navigationController.get(),
                    )
            }
            //............ *******

        @ScopeAppUiPage
        class PageDiscoverAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.auth.discover.PageDiscoverAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.auth.discover.PageDiscoverAction.create(
                    navigationController.get()
                )
        }

        @ScopeAppUiPage
        class PageHelpAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.auth.help.PageHelpAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.auth.help.PageHelpAction.create(
                    navigationController.get()
                )
        }

        @ScopeAppUiPage
        class PagePaymentAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.auth.payment.PagePaymentAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.auth.payment.PagePaymentAction.create(
                    navigationController.get()
                )
        }

        @ScopeAppUiPage
        class PageProfileAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
            private val dialogAction: ModuleCoreUiActivity.Action.DialogAction,
        ) : ComposableWrapper<com.tezov.bank.ui.pageMain.auth.profile.PageProfileAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageMain.auth.profile.PageProfileAction.create(
                    navigationController.get(),
                    dialogAction.get()
                )
        }
        //............ *******

        //--COMMON-- *******
        @ScopeAppUiPage
        class PageWebViewAction @Inject constructor(
            private val navigationController: ModuleCoreUiActivity.Action.NavigationController,
        ) : ComposableWrapper<com.tezov.bank.ui.pageSecondary.common.PageWebViewAction>() {
            @androidx.compose.runtime.Composable
            override fun create() =
                com.tezov.bank.ui.pageSecondary.common.PageWebViewAction.create(
                    navigationController.get(),
                )
        }
        //............ *******
    }



}