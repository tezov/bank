/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.tezov.lib_core_android_kotlin.navigation.NavigationRouteManager.Route
import com.tezov.lib_core_android_kotlin.navigation.navigator.composable.ComposableTransientNavigator
import com.tezov.lib_core_android_kotlin.navigation.navigator.composableOverlay.ComposableOverlayNavigator
import com.tezov.lib_core_android_kotlin.ui.compositionTree.base.CompositionAction
import com.tezov.lib_core_android_kotlin.ui.extension.ExtensionActivity.activity
import com.tezov.lib_core_kotlin.extension.ExtensionBoolean.isTrueOrNull
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull
import com.tezov.lib_core_kotlin.type.collection.ListEntry
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class NavigationController constructor(
    internal val navHostController: NavHostController,
    private val navigationNotifier: NavigationNotifier,
    private val _routes: NavigationRouteManager = NavigationRouteManager(),
) {

    companion object {
        @Composable
        fun create(
            navigationNotifier: NavigationNotifier,
            navHostController: NavHostController = rememberSaveableNavigationController()
        ) = NavigationController(
            navHostController = navHostController,
            navigationNotifier = navigationNotifier,
        )

        @Composable
        private fun rememberSaveableNavigationController(): NavHostController {
            val context = LocalContext.current
            val saver = Saver<NavHostController, Bundle>(
                save = { it.saveState() },
                restore = { createNavController(context).apply { restoreState(it) } }
            )
            return rememberSaveable(saver = saver) {
                createNavController(context)
            }
        }

        private fun createNavController(context: Context) = NavHostController(context).apply {
            navigatorProvider.addNavigator(ComposableTransientNavigator())
            navigatorProvider.addNavigator(ComposableOverlayNavigator())
        }
    }

    interface Friend

    class Option(
        val singleTop: Boolean? = null,
        val popUpTo: PopUpTo? = null,
        val clearStack: Boolean? = null,
    ) {

        companion object {
            fun ClearStack() = Option(clearStack = true)

            fun SingleTop(route: Route) =
                Option(
                    singleTop = true,
                    popUpTo = Option.PopUpTo(route, false)
                )

            fun PopUpTo(route: Route, singleTop: Boolean? = null, inclusive: Boolean = true) =
                Option(
                    singleTop = singleTop,
                    popUpTo = Option.PopUpTo(route, inclusive)
                )
        }

        class PopUpTo(val route: Route, val inclusive: Boolean)

        fun build(graph: NavGraph, builder: NavOptionsBuilder) = builder.apply {
            this@Option.singleTop?.let {
                launchSingleTop = it
            }
            this@Option.popUpTo?.let {
                popUpTo(it.route.path) {
                    inclusive = it.inclusive
                }
            }
            this@Option.clearStack?.let {
                popUpTo(graph.id) {
                    inclusive = true
                }
            }
        }

    }

    class Request(
        val from: Route? = null,
        val to: Route,
        val askedBy: CompositionAction<*>?,
        var option: Option? = null
    )

    init {
        //TODO
        // Solution 1 ->
        // listener sur navHostController not ok, reception trop tard, passage qui obligera une recomposition
        // recupération avant la navigate et pass a travers le navigationNotifier ok sur appli avec toutes les donneés dispo
        // mais trop tard aussi sur appli avec chargement sur réseaux parce qu'on navigue sans avoir la garantie que la page
        // est capable de se construire.
        // ---
        // Solution 2 -> better,
        // lock navigation et affiche ou non un message de chargement
        // demande au view Model de la disponibilité des données ou le chargement
        // quand le viewModel donne son accord, démarre la navigation
    }

    private var currentRequest: Request? = null
        set(value) {
            field = value
            field?.let {
                isNavigatingBack = it.to is NavigationRouteManager.Back
            }
        }

    val isIdle get() = currentRequest == null

    var isNavigatingBack = false
        private set

    val backQueue @Composable get() = navHostController.currentBackStack

    val isLastRoute get() = navHostController.previousBackStackEntry == null

    private val requestHandlers: ListEntry<KClass<out Any>, (request: Request) -> Unit> =
        ListEntry()

    fun currentRoute(copyArgument: Boolean = false): Route? {
        val entry = if (isIdle || isNavigatingBack) {
            navHostController.currentBackStackEntry
        } else {
            navHostController.previousBackStackEntry
        }
        return entry?.route(copyArgument = copyArgument)
    }

    fun finalRoute(copyArgument: Boolean = false) =
        navHostController.currentBackStackEntry?.route(copyArgument = copyArgument)

    private fun NavBackStackEntry.route(copyArgument: Boolean = false) =
        _routes.find(this.destination.route)?.let { route ->
            val routeCopy = if (copyArgument) {
                navHostController.currentBackStackEntry?.arguments?.let { route.copy(it) }
            } else null
            routeCopy ?: route
        }

    fun addRequestHandler(
        friend: Friend,
        klass: KClass<out CompositionAction<*>>,
        handler: (request: Request) -> Unit
    ) {
        requestHandlers.add(klass, handler)
    }

    fun addRequestHandler(
        friend: Friend,
        handlerMap: Map<KClass<out CompositionAction<*>>, ((request: Request) -> Unit)>
    ) {
        handlerMap.forEach { (klass, action) ->
            requestHandlers.add(klass, action)
        }
    }

    fun setRequestExceptionHandler(
        friend: Friend,
        handler: (request: Request) -> Unit
    ) {
        requestHandlers.put(Exception::class, handler)
    }

    fun setRequestFeedbackHandler(
        friend: Friend,
        handler: (request: Request) -> Unit
    ) {
        requestHandlers.put(NavigationRouteManager.RequestFeedback::class, handler)
    }

    fun routes(friend: Friend) = _routes

    fun onBackPressedDispatch() = handleOnBackPressed()

    fun handleOnBackPressed() = navigateBack().isTrueOrNull

    private fun lockNavigate(request: Request): Boolean {
        if (currentRequest != null) {
            requestFeedback(request, IllegalStateException("Navigation is busy"))
            return false
        }
        currentRequest = request
        return true
    }

    internal fun unlockNavigate() {
        currentRequest?.let {
            requestFeedback(it, null)
            currentRequest = null
        }
    }

    private fun requestFeedback(request: Request, exception: Throwable?) {
        val failedRequest = Request(
            from = request.from,
            to = NavigationRouteManager.RequestFeedback(
                target = request.to,
                exception = exception,
            ),
            askedBy = request.askedBy
        )
        requestHandlers.getValue(NavigationRouteManager.RequestFeedback::class)
            ?.invoke(failedRequest)
    }

    fun navigate(friend: Friend, request: Request) {
        if (!lockNavigate(request)) {
            return
        }
        if (request.to == NavigationRouteManager.Finish) {
            navHostController.context.activity.finishAffinity()
        } else {
            navigationNotifier.onNavigate(request = request)
            request.option?.let { option ->
                navHostController.navigate(route = request.to.query, builder = {
                    option.build(navHostController.graph, this)
                })
            } ?: kotlin.run {
                navHostController.navigate(route = request.to.query)
            }
        }
    }

    private fun navigateBack() = navigateBack(
        object : Friend {},
        Request(
            from = currentRoute(),
            to = NavigationRouteManager.Back,
            askedBy = null
        )
    )

    fun navigateBack(friend: Friend, request: Request): Boolean? {
        if (!lockNavigate(request)) {
            return null
        }
        if (!isLastRoute) {
            navigationNotifier.onNavigate(request = request)
            navHostController.popBackStack()
            return true
        }
        return false
    }

//    ***** Public Access

    fun requestNavigate(to: Route, askedBy: CompositionAction<*>) {
        requestNavigate(
            Request(
                from = currentRoute(),
                to = to,
                askedBy = askedBy
            )
        )
    }

    fun requestNavigate(request: Request) {
        requestHandlers.find { entry ->
            request.askedBy?.let { askedBy ->
                askedBy::class.isInstance(entry.key) || askedBy::class.isSubclassOf(entry.key)
            } ?: false
        }?.value?.invoke(request) ?: run {
            requestHandlers.getValue(Exception::class)?.invoke(request)
        }
    }

    fun requestNavigateBack(askedBy: CompositionAction<*>) {
        requestNavigate(NavigationRouteManager.Back, askedBy)
    }

}