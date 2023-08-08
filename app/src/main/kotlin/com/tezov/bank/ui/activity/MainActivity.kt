

package com.tezov.bank.ui.activity

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.tezov.bank.application.LocalApplication
import com.tezov.bank.navigation.NavigationGraph
import com.tezov.bank.navigation.bottom_navigation.BottomNavigationItems
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiActivity
import com.tezov.bank.ui.theme.ThemeApplication
import com.tezov.lib_adr_app_core.navigation.bottom_navigation.BottomNavigation
import com.tezov.lib_adr_app_core.navigation.top_app_bar.TopAppBar
import com.tezov.lib_adr_app_core.navigation.top_app_bar.TopAppBarItemData
import com.tezov.lib_adr_app_core.ui.composition.activity.ActivityBase
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.bottomsheet.BottomSheet
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.dialog.Dialog
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.snackbar.Snackbar
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.Activity
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.state
import com.tezov.lib_adr_ui_core.theme.style.padding
import com.tezov.lib_adr_ui_core.theme.theme.dimensionsPaddingExtended
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ActivityBase<MainActivityState, MainActivityAction>() {

    @Composable
    override fun Activity<MainActivityState, MainActivityAction>.content() {
        ThemeApplication.BankDefault {
            DiAccessorAppUiActivity().remember(requester = this)
            LocalApplication.composeInit()
            NavigationGraph()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: find a better hack to fix white screen at start on Xiaomi
        //know bug https://issuetracker.google.com/issues/227926002
        //and my phone is a xiaomi xD
        lifecycleScope.launch {
            delay(50)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    @Composable
    fun withTopAppBarAndBottomNavigationBar(
        topAppBarTitleResourceId: Int,
        topAppBarLeadingItem: TopAppBarItemData? = null,
        topAppBarTrailingItem: TopAppBarItemData? = null,
        content: @Composable (PaddingValues) -> Unit
    ) {
        val accessor = DiAccessorAppUiActivity().with(key = this)
        val mainState = accessor.contextMain().state()
        BottomSheet {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.Transparent,
                scaffoldState = mainState.scaffoldState,
                bottomBar = {
                    BottomNavigation(BottomNavigationItems.items)
                },
                topBar = {
                    TopAppBar(
                        topAppBarTitleResourceId,
                        topAppBarLeadingItem,
                        topAppBarTrailingItem
                    )
                },
                snackbarHost = {
                    Snackbar(
                        modifier = Modifier.padding(MaterialTheme.dimensionsPaddingExtended.block.normal)
                    )
                },
                content = content
            )
        }
        Dialog()
    }

    @Composable
    fun withTopAppBar(
        topAppBarTitleResourceId: Int,
        topAppBarLeadingItem: TopAppBarItemData? = null,
        topAppBarTrailingItem: TopAppBarItemData? = null,
        content: @Composable (PaddingValues) -> Unit
    ) {
        val accessor = DiAccessorAppUiActivity().with(key = this)
        val mainState = accessor.contextMain().state()
        BottomSheet {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.Transparent,
                scaffoldState = mainState.scaffoldState,
                topBar = {
                    TopAppBar(
                        topAppBarTitleResourceId,
                        topAppBarLeadingItem,
                        topAppBarTrailingItem
                    )
                },
                snackbarHost = {
                    Snackbar(
                        modifier = Modifier.padding(MaterialTheme.dimensionsPaddingExtended.block.normal)
                    )
                },
                content = content
            )
        }
        Dialog()
    }

    @Composable
    fun withBottomNavigationBar(
        content: @Composable (PaddingValues) -> Unit
    ) {
        val accessor = DiAccessorAppUiActivity().with(key = this)
        val mainState = accessor.contextMain().state()
        BottomSheet {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.Transparent,
                scaffoldState = mainState.scaffoldState,
                bottomBar = {
                    BottomNavigation(BottomNavigationItems.items)
                },
                topBar = { },
                snackbarHost = {
                    Snackbar(
                        modifier = Modifier.padding(MaterialTheme.dimensionsPaddingExtended.block.normal)
                    )
                },
                content = content
            )
        }
        Dialog()
    }

    @Composable
    fun empty(
        content: @Composable (PaddingValues) -> Unit
    ) {
        val accessor = DiAccessorAppUiActivity().with(key = this)
        val mainState = accessor.contextMain().state()
        BottomSheet {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.Transparent,
                scaffoldState = mainState.scaffoldState,
                snackbarHost = {
                    Snackbar(
                        modifier = Modifier.padding(MaterialTheme.dimensionsPaddingExtended.block.normal)
                    )
                },
                content = content
            )
        }
        Dialog()
    }
}

