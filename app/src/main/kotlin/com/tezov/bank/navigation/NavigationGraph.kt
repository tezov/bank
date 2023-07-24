

package com.tezov.bank.navigation

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.tezov.bank.R
import com.tezov.bank.navigation.NavigationRoutes.Route
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
import com.tezov.lib_core_android_kotlin.navigation.NavHost
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation
import com.tezov.lib_core_android_kotlin.navigation.NavigationAnimation.Slide.Effect
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager
import com.tezov.lib_core_android_kotlin.navigation.navigator.GraphEntry
import com.tezov.lib_core_android_kotlin.navigation.navigator.composable.composableTransient
import com.tezov.lib_core_android_kotlin.navigation.navigator.composableOverlay.composableOverlay
import com.tezov.lib_core_android_kotlin.navigation.top_app_bar.TopAppBarItemData
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.Activity.Companion.LocalActivity
import com.tezov.lib_core_android_kotlin.ui.di.common.ExtensionCoreUi.action

object NavigationGraph {

    @Composable
    operator fun invoke() {
        val accessor = DiAccessorAppUiActivity().with(LocalActivity.current)
        val mainAction = accessor.contextMain().action()

        NavHost(
            navController = mainAction.navigationRoutes.controller,
            startRoute = NavigationRoutes.startNavRoute,
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
                startRoute = NavigationRoutes.startLobbyRoute
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
            }
            navigation(
                route = Route.NavAuth,
                startRoute = NavigationRoutes.startAuthRoute
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
                                icon = R.drawable.ic_arrow_left_24dp,
                                route = NavigationRouteManager.Back
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

    private fun NavGraphBuilder.navigation(
        startRoute: NavigationRouteManager.Route,
        route: NavigationRouteManager.Route,
        builder: NavGraphBuilder.() -> Unit
    ) = navigation(
        startDestination = startRoute.path,
        route = route.path,
        builder = builder,
    )

    private fun NavGraphBuilder.composableTransient(
        route: NavigationRouteManager.Route,
        arguments: List<NamedNavArgument> = emptyList(),
        animationConfig: NavigationAnimation.Config? = null,
        content: @Composable BoxScope.(GraphEntry) -> Unit
    ) = composableTransient(
        route = route.path,
        arguments = arguments,
        animationConfig = animationConfig,
        content = content
    )

    private fun NavGraphBuilder.composableOverlay(
        route: NavigationRouteManager.Route,
        arguments: List<NamedNavArgument> = emptyList(),
        animationConfig: NavigationAnimation.Config? = null,
        content: @Composable BoxScope.(GraphEntry) -> Unit,
    ) = composableOverlay(
        route = route.path,
        arguments = arguments,
        animationConfig = animationConfig,
        content = content
    )

}