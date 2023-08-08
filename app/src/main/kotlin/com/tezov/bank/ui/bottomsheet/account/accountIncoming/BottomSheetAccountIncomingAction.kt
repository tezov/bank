

package com.tezov.bank.ui.bottomsheet.account.accountIncoming

import com.tezov.lib_adr_app_core.ui.compositionTree.modal.bottomSheet.BottomSheetAction

class BottomSheetAccountIncomingAction private constructor(
    private val action: com.tezov.lib_adr_app_core.ui.composition.activity.sub.bottomsheet.BottomSheetAction,
) : BottomSheetAction<BottomSheetAccountIncomingState> {


    companion object {
        fun create(
            action: com.tezov.lib_adr_app_core.ui.composition.activity.sub.bottomsheet.BottomSheetAction,
        ) = BottomSheetAccountIncomingAction(
            action = action,
        )
    }


    fun onClickClose() {
        action.close()
    }

}