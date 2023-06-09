/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.bank.ui.di.component

import com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncomingAction
import com.tezov.bank.ui.bottomsheet.account.accountIncoming.BottomSheetAccountIncomingState
import com.tezov.bank.ui.di.module.ModuleAppUiModal
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmationAction
import com.tezov.bank.ui.dialog.auth.closeAppConfirmation.DialogCloseAppConfirmationState
import com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgottenAction
import com.tezov.bank.ui.dialog.lobby.loginForgotten.DialogLoginForgottenState
import com.tezov.lib_core_android_kotlin.ui.di.annotation.scope.ScopeAppUiModal
import com.tezov.lib_core_android_kotlin.ui.di.common.ComposableContext
import com.tezov.lib_core_android_kotlin.ui.di.component.ComponentCoreUiModal
import dagger.Component

object ComponentAppUiModal {

    @ScopeAppUiModal
    @Component(
        dependencies = [ComponentCoreUiModal.EntryPoint::class, ComponentAppUiPage.EntryPoint::class],
        modules = [ModuleAppUiModal.MapperContext::class]
    )
    interface EntryPoint {

        @Component.Factory
        interface Factory {
            fun create(
                componentCore: ComponentCoreUiModal.EntryPoint,
                componentAppPage: ComponentAppUiPage.EntryPoint
            ): EntryPoint
        }

        fun contextCloseAppConfirmation(): ComposableContext<DialogCloseAppConfirmationState, DialogCloseAppConfirmationAction>
        fun contextLoginForgotten(): ComposableContext<DialogLoginForgottenState, DialogLoginForgottenAction>

        fun contextAccountIncoming(): ComposableContext<BottomSheetAccountIncomingState, BottomSheetAccountIncomingAction>

    }

}

