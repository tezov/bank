

package com.tezov.bank.navigation

import com.tezov.lib_adr_app_core.navigation.NavigationController.Option
import com.tezov.lib_adr_app_core.navigation.NavigationController.Request
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager
import com.tezov.lib_adr_app_core.navigation.NavigationRouteManager.*
import com.tezov.lib_adr_app_core.navigation.bottom_navigation.BottomNavigationAction
import com.tezov.lib_adr_app_core.navigation.top_app_bar.TopAppBarAction
import com.tezov.lib_adr_app_core.ui.composition.activity.sub.snackbar.SnackbarAction
import com.tezov.lib_adr_app_core.ui.compositionTree.modal.dialog.DialogAction
import com.tezov.lib_adr_app_core.ui.compositionTree.page.PageAction

class NavigationRoutes(
    val controller: com.tezov.lib_adr_app_core.navigation.NavigationController,
    private val snackbarAction: SnackbarAction,
) : com.tezov.lib_adr_app_core.navigation.NavigationController.Friend {

    companion object Route {

        //Command        *********************************************
        class NotHandled private constructor(val request: Request?) :
            NavigationRouteManager.Route("not_handled") {
            companion object {
                private val default = NotHandled(null)
                operator fun invoke() = default
                fun wrap(request: Request) = Request(
                    to = NotHandled(request),
                    askedBy = null
                )
            }
        }

        //Graph        *********************************************
        //COMMON
        class WebView(url:String?): NavigationRouteManager.Route("web_view"){

            companion object {
                private const val NAME_URL = "url"
                private val default = WebView(null)
                operator fun invoke() = default

            }

            init {
                putParameter(NAME_URL, url)
            }

            override fun createRoute() = WebView(id)

            var url:String
                get() = getParameter(NAME_URL) ?: ""
                set(value) {
                    putParameter(NAME_URL, value)
                }

        }

        //LOBBY
        object Splash : NavigationRouteManager.Route("splash")
        object Lounge : NavigationRouteManager.Route("lounge")
        object LoginAuth : NavigationRouteManager.Route("login_auth")
        object HelpAndService : NavigationRouteManager.Route("help_and_service")
        object NavLobby : Routes(
            "navLobby",
            child = setOf(Splash, Lounge, LoginAuth, HelpAndService)
        )

        //AUTH
        object Account : NavigationRouteManager.Route("account")
        object MessageInfo : NavigationRouteManager.Route("messageInfo")
        object NavAccount : Routes(
            "navAccount",
            child = setOf(Account, MessageInfo)
        )

        object Discover : NavigationRouteManager.Route("discover")
        object Payment : NavigationRouteManager.Route("payment")
        object Help : NavigationRouteManager.Route("help")
        object Profile : NavigationRouteManager.Route("profile")
        object NavAuth : Routes(
            "navAuth",
            child = setOf(NavAccount, Discover, Payment, Help, Profile)
        )

        val startNavRoute = NavLobby
        val startLobbyRoute = Splash
        val startAuthRoute = NavAccount
    }

    init {
        controller.routes(this@NavigationRoutes).apply {
            add(NavLobby)
            add(NavAuth)
            add(WebView())
            add(NotHandled())
        }
        controller.addRequestHandler(
            this@NavigationRoutes,
            mapOf(
                TopAppBarAction::class to this::navigateFromTopAppBar,
                BottomNavigationAction::class to this::navigateFromBottomNavigation,
                PageAction::class to this::navigateFromPage,
                DialogAction::class to this::navigateFromDialog,
            )
        )
        controller.setRequestExceptionHandler(
            this@NavigationRoutes,
            this::navigateException,
        )
        controller.setRequestFeedbackHandler(
            this@NavigationRoutes,
            this::navigateFeedback,
        )
    }

    private fun navigateFeedback(request: Request) {
         (request.to as? RequestFeedback)?.let {
            //navigation is busy, request was refused
        }
    }

    private fun navigateException(request: Request?) {
        request?.let {
            (request.to as? NotHandled)?.request?.takeIf { it.to is NotImplemented }?.let {
                return navigateException(it)
            }
            (request.to as? NotImplemented)?.let {
                snackbarAction.showNotImplemented(it.message)
            } ?: (request.to as? NotHandled)?.let {
                snackbarAction.show("forget to handle route from ${it.request?.from?.path} to ${it.request?.to?.path}")
            }
        } ?: kotlin.run {
            snackbarAction.showNotImplemented()
        }
    }

    private fun navigateFromTopAppBar(request: Request) {
        with(controller) {
            var failedToNavigate = true
            when (request.to) {
                Back -> {
                    navigateBack(
                        this@NavigationRoutes,
                        request = request
                    )
                    failedToNavigate = false
                }
            }
            if (failedToNavigate) {
                navigateException(NotHandled.wrap(request))
            }
        }
    }

    private fun navigateFromBottomNavigation(request: Request) {
        with(controller) {
            navigate(
                this@NavigationRoutes,
                request = request.apply {
                    option = option ?: Option.SingleTop(request.to)
                }
            )
        }
    }

    private fun navigateFromPage(request: Request) {
        with(controller) {
            var failedToNavigate = true
            when (request.from) {
                //LOBBY
                Splash -> {
                    when (request.to) {
                        Lounge -> {
                            navigate(
                                this@NavigationRoutes,
                                request = request.apply {
                                    option = option ?: Option.ClearStack()
                                }
                            )
                            failedToNavigate = false
                        }
                    }
                }
                Lounge -> {
                    when (request.to) {
                        LoginAuth -> {
                            navigate(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                        HelpAndService -> {
                            navigate(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                    }
                }
                LoginAuth -> {
                    when (request.to) {
                        NavAuth -> {
                            navigate(
                                this@NavigationRoutes,
                                request = request.apply {
                                    option = option ?: Option.ClearStack()
                                }
                            )
                            failedToNavigate = false
                        }
                        is WebView -> {
                            navigate(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                        Back -> {
                            navigateBack(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                    }
                }
                HelpAndService -> {
                    when (request.to) {
                        Back -> {
                            navigateBack(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                    }
                }
                //AUTH
                Account -> {
                    when (request.to) {
                        MessageInfo -> {
                            navigate(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                    }
                }
                //COMMON
                is WebView -> {
                    when (request.to) {
                        Back -> {
                            navigateBack(
                                this@NavigationRoutes,
                                request = request
                            )
                            failedToNavigate = false
                        }
                    }
                }
            }
            if (failedToNavigate) {
                navigateException(NotHandled.wrap(request))
            }
        }
    }

    private fun navigateFromDialog(request: Request) {
        with(controller) {
            var failedToNavigate = true
            when (request.from) {
                Account, Discover, Payment, Help, Profile -> {
                    when (request.to) {
                        Finish -> {
                            navigate(
                                this@NavigationRoutes,
                                request
                            )
                            failedToNavigate = false
                        }
                    }
                }
            }
            if (failedToNavigate) {
                navigateException(NotHandled.wrap(request))
            }
        }
    }

}




