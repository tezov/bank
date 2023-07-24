

package com.tezov.bank.ui.activity

import androidx.compose.runtime.Composable
import com.tezov.bank.navigation.NavigationRoutes
import com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.snackbar.SnackbarAction
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.ActivityAction

class MainActivityAction private constructor(
    val navigationRoutes: NavigationRoutes,
) : ActivityAction<MainActivityState> {

    companion object {
        @Composable
        fun create(
            navigationController: com.tezov.lib_core_android_kotlin.navigation.NavigationController,
            snackbarAction: SnackbarAction,
        ): MainActivityAction {
            return MainActivityAction(
                navigationRoutes = NavigationRoutes(
                    controller = navigationController,
                    snackbarAction = snackbarAction,
                ),
            )
        }
    }

}