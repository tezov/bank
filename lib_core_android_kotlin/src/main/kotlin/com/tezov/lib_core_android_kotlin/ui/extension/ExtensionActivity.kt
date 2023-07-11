/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.extension

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

object ExtensionActivity {

    val Context.activity: ComponentActivity
        get() {
            if (this is ComponentActivity) {
                return this
            }
            if (this is ContextWrapper) {
                var activity = this.baseContext
                while (true) {
                    if (activity is ComponentActivity) {
                        return activity
                    }
                    activity = if (activity is ContextWrapper) {
                        activity.baseContext
                    } else {
                        break
                    }
                }
            }
            throw NullPointerException("Failed to get activity from context")
        }

}