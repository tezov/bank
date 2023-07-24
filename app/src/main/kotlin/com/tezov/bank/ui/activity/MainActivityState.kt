

package com.tezov.bank.ui.activity

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.composition.activity.ActivityCoreState
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.ActivityState

class MainActivityState private constructor(
    val scaffoldState: ScaffoldState
) : ActivityState {

    companion object {
        @Composable
        fun create(scaffoldState: ScaffoldState) = MainActivityState(
            scaffoldState = scaffoldState
        )
    }

}