

package com.tezov.bank.ui.di.component

import com.tezov.bank.ui.di.accessor.DiAccessorAppUiModal
import com.tezov.bank.ui.di.module.ModuleAppUiPage
import com.tezov.bank.ui.pageMain.auth.account.PageAccountAction
import com.tezov.bank.ui.pageMain.auth.account.PageAccountState
import com.tezov.bank.ui.pageMain.auth.discover.PageDiscoverAction
import com.tezov.bank.ui.pageMain.auth.discover.PageDiscoverState
import com.tezov.bank.ui.pageMain.auth.help.PageHelpAction
import com.tezov.bank.ui.pageMain.auth.help.PageHelpState
import com.tezov.bank.ui.pageMain.auth.payment.PagePaymentAction
import com.tezov.bank.ui.pageMain.auth.payment.PagePaymentState
import com.tezov.bank.ui.pageMain.auth.profile.PageProfileAction
import com.tezov.bank.ui.pageMain.auth.profile.PageProfileState
import com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceAction
import com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndServiceState
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthAction
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuthState
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeAction
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLoungeState
import com.tezov.bank.ui.pageMain.lobby.splash.PageSplashAction
import com.tezov.bank.ui.pageMain.lobby.splash.PageSplashState
import com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfoAction
import com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfoState
import com.tezov.bank.ui.pageSecondary.common.PageWebViewAction
import com.tezov.bank.ui.pageSecondary.common.PageWebViewState
import com.tezov.bank.ui.di.annotation.scope.ScopeAppUiPage
import com.tezov.lib_adr_app_core.ui.di.common.ComposableContext
import com.tezov.lib_adr_app_core.ui.di.component.ComponentCoreUiActivity
import com.tezov.lib_adr_app_core.ui.di.component.ComponentCoreUiPage
import dagger.Component

object ComponentAppUiPage {

    @ScopeAppUiPage
    @Component(
        dependencies = [ComponentCoreUiPage.EntryPoint::class, ComponentAppUiActivity.EntryPoint::class],
        modules = [ModuleAppUiPage.MapperContext::class]
    )
    interface EntryPoint : ComponentCoreUiActivity.Exposer {

        @Component.Factory
        interface Factory {
            fun create(
                componentCorePage: ComponentCoreUiPage.EntryPoint,
                componentAppActivity: ComponentAppUiActivity.EntryPoint
            ): EntryPoint
        }

        fun accessorDialog(): DiAccessorAppUiModal

        fun controllerDialogAuthCloseApp(): ModuleAppUiPage.Provided.DialogAuthCloseAppController

        //--LOBBY-- *******
        fun contextSplash(): ComposableContext<PageSplashState, PageSplashAction>
        fun contextLounge(): ComposableContext<PageLoungeState, PageLoungeAction>
        fun contextLoginAuth(): ComposableContext<PageLoginAuthState, PageLoginAuthAction>
        fun contextHelpAndService(): ComposableContext<PageHelpAndServiceState, PageHelpAndServiceAction>
        //............ *******

        //--AUTH-- *******
            //--account-- *******
            fun contextAccount(): ComposableContext<PageAccountState, PageAccountAction>
            fun contextMessageInfo(): ComposableContext<PageMessageInfoState, PageMessageInfoAction>
            //............ *******
        fun contextDiscover(): ComposableContext<PageDiscoverState, PageDiscoverAction>
        fun contextPayment(): ComposableContext<PagePaymentState, PagePaymentAction>
        fun contextProfile(): ComposableContext<PageProfileState, PageProfileAction>
        fun contextHelp(): ComposableContext<PageHelpState, PageHelpAction>
        //............ *******

        //--COMMON-- *******
        fun contextWebView(): ComposableContext<PageWebViewState, PageWebViewAction>
        //............ *******


    }


}

