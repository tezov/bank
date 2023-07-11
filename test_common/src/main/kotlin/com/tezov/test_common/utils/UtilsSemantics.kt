/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.test_common.utils

import android.util.Log
import androidx.compose.ui.test.SemanticsNodeInteraction

object UtilsSemantics {

    fun SemanticsNodeInteraction.toDebugLog() {
        val config = this.fetchSemanticsNode().config
        for ((key, value) in config) {
            Log.d(">>:", "$key : ${key.name} = $value")
        }
    }

}