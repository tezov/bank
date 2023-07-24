

package com.tezov.bank.ui.bottomsheet.account.accountIncoming

import com.tezov.lib_core_android_kotlin.navigation.NavigationController
import com.tezov.lib_core_android_kotlin.ui.compositionTree.modal.bottomSheet.BottomSheetAction

class BottomSheetAccountIncomingAction private constructor(
    private val action: com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetAction,
) : BottomSheetAction<BottomSheetAccountIncomingState> {


    companion object {
        fun create(
            action: com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet.BottomSheetAction,
        ) = BottomSheetAccountIncomingAction(
            action = action,
        )
    }


    fun onClickClose() {
        action.close()
    }

}