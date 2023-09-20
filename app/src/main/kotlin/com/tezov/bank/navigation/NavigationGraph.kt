

package com.tezov.bank.navigation

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.tezov.app.R
import com.tezov.bank.ui.activity.MainActivity
import com.tezov.bank.ui.di.accessor.DiAccessorAppUiActivity
import com.tezov.bank.ui.pageMain.auth.account.PageAccount
import com.tezov.bank.ui.pageMain.auth.discover.PageDiscover
import com.tezov.bank.ui.pageMain.auth.help.PageHelp
import com.tezov.bank.ui.pageMain.auth.payment.PagePayment
import com.tezov.bank.ui.pageMain.auth.profile.PageProfile
import com.tezov.bank.ui.pageMain.lobby.help_and_service.PageHelpAndService
import com.tezov.bank.ui.pageMain.lobby.loginAuth.PageLoginAuth
import com.tezov.bank.ui.pageMain.lobby.lounge.PageLounge
import com.tezov.bank.ui.pageMain.lobby.splash.PageSplash
import com.tezov.bank.ui.pageSecondary.auth.messageInfo.PageMessageInfo
import com.tezov.bank.ui.pageSecondary.common.PageWebView
import com.tezov.lib_adr_app_core.navigation.NavHost
import com.tezov.lib_adr_app_core.navigation.NavigationAnimation
import com.tezov.lib_adr_app_core.navigation.NavigationAnimation.Slide.Effect
import com.tezov.lib_adr_app_core.navigation.top_app_bar.TopAppBarItemData
import com.tezov.lib_adr_app_core.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_adr_app_core.ui.di.common.ExtensionCoreUi.action
import com.tezov.bank.navigation.NavigationRouteManager.Route
import com.tezov.lib_adr_app_core.navigation.navigator.composableOverlay
import com.tezov.lib_adr_app_core.navigation.navigator.composableTransient
import com.tezov.lib_adr_app_core.navigation.navigator.navigation

object NavigationGraph {

    @Composable
    operator fun invoke() {
        val accessor = DiAccessorAppUiActivity().with(LocalActivity.current)
        val mainAction = accessor.contextMain().action()

        NavHost(
            navController = mainAction.navigationRouteManager.controller,
            startRoute = NavigationRouteManager.startNavRoute,
        ) {
            composableTransient(
                route = Route.WebView(),
                animationConfig = NavigationAnimation.Config(
                    default = NavigationAnimation.Config.Type.SlideVertical(
                        outDarkAlphaFactor = 0.0f
                    )
                )
            ) { graphEntry ->
                (LocalActivity.current as MainActivity).empty { innerPadding ->
                    PageWebView(graphEntry = graphEntry, innerPadding = innerPadding)
                }
            }
            navigation(
                route = Route.NavLobby,
                startRoute = NavigationRouteManager.startLobbyRoute
            ) {
                composableTransient(
                    route = Route.Splash
                ) { graphEntry ->
                    PageSplash(graphEntry = graphEntry, innerPadding = PaddingValues())
                }
                composableTransient(
                    route = Route.Lounge,
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).empty { innerPadding ->
                        PageLounge(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
                composableOverlay(
                    route = Route.LoginAuth,
                    animationConfig = NavigationAnimation.Config{
                        enter {
                            pop = NavigationAnimation.Config.Type.None
                        }
                        exit {
                            push = NavigationAnimation.Config.Type.None
                        }
                    }
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).empty { innerPadding ->
                        PageLoginAuth(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
                composableTransient(
                    route = Route.HelpAndService,
                    animationConfig = NavigationAnimation.Config(
                        default = NavigationAnimation.Config.Type.SlideHorizontal()
                    )
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).empty { innerPadding ->
                        PageHelpAndService(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
                composableTransient(
                    route = Route.Terms,
                    animationConfig = NavigationAnimation.Config(
                        default = NavigationAnimation.Config.Type.SlideHorizontal(
                            entrance = NavigationAnimation.Slide.Horizontal.Entrance.FromStart,
                        )
                    )
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).empty { innerPadding ->
                        PageWebView(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
            }
            navigation(
                route = Route.NavAuth,
                startRoute = NavigationRouteManager.startAuthRoute
            ) {
                navigation(
                    route = Route.NavAccount.id,
                    startDestination = Route.Account.id
                ) {
                    composableTransient(
                        route = Route.Account
                    ) { graphEntry ->
                        (LocalActivity.current as MainActivity).withBottomNavigationBar { innerPadding ->
                            PageAccount(graphEntry = graphEntry, innerPadding = innerPadding)
                        }
                    }
                    composableTransient(
                        route = Route.MessageInfo,
                        animationConfig = NavigationAnimation.Config(
                            default = NavigationAnimation.Config.Type.SlideVertical(
                                effect = Effect.Cover
                            )
                        )
                    ) { graphEntry ->
                        (LocalActivity.current as MainActivity).withTopAppBar(
                            topAppBarTitleResourceId = R.string.nav_top_message_info,
                            topAppBarLeadingItem = object : TopAppBarItemData(
                                icon = com.tezov.lib_adr_ui_cpt.R.drawable.ic_arrow_left_24dp,
                                route = com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.Route.Back()
                            ) {}
                        ) { innerPadding ->
                            PageMessageInfo(graphEntry = graphEntry, innerPadding = innerPadding)
                        }
                    }
                }
                composableTransient(
                    route = Route.Discover
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).withBottomNavigationBar { innerPadding ->
                        PageDiscover(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
                composableTransient(
                    route = Route.Help
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).withBottomNavigationBar { innerPadding ->
                        PageHelp(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
                composableTransient(
                    route = Route.Profile
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).withBottomNavigationBar { innerPadding ->
                        PageProfile(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
                composableTransient(
                    route = Route.Payment
                ) { graphEntry ->
                    (LocalActivity.current as MainActivity).withBottomNavigationBar { innerPadding ->
                        PagePayment(graphEntry = graphEntry, innerPadding = innerPadding)
                    }
                }
            }
        }
    }
}