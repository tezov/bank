

package com.tezov.bank.ui.activity

import androidx.compose.runtime.Composable
import com.tezov.bank.navigation.NavigationRoutes
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.snackbar.SnackbarAction
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.ActivityAction

class MainActivityAction private constructor(
    val navigationRoutes: NavigationRoutes,
) : ActivityAction<MainActivityState> {

    companion object {
        @Composable
        fun create(
            navigationController: com.tezov.lib_adr_app_core.navigation.NavigationController,
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