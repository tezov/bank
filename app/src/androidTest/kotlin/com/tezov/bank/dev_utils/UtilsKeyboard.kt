

package com.tezov.bank.dev_utils

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

object UtilsKeyboard {

    enum class Status {
        Opened, Closed
    }

    @Composable
    fun AsState(): State<Status> {
        val keyboardState = remember { mutableStateOf(Status.Closed) }
        val view = LocalView.current
        DisposableEffect(view) {
            val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
                val rect = Rect()
                view.getWindowVisibleDisplayFrame(rect)
                val screenHeight = view.rootView.height
                val keypadHeight = screenHeight - rect.bottom
                keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                    Status.Opened
                } else {
                    Status.Closed
                }
            }
            view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
            onDispose {
                view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
            }
        }
        return keyboardState
    }

}