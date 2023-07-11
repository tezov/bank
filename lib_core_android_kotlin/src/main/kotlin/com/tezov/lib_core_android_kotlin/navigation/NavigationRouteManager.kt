/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.navigation

import android.os.Bundle
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNull
import com.tezov.lib_core_kotlin.type.collection.ListEntry
import com.tezov.lib_core_kotlin.type.primitive.string.StringCharTo.toStringHex
import com.tezov.lib_core_kotlin.type.primitive.string.StringHexTo.toStringChar


class NavigationRouteManager {

    open class Route(val id: String, parent: Route? = null) {

        companion object {
            private const val HAS_PARAMETER = "!"
            private const val PARAMETER_NULL = "?"
        }

        var parent: Route? = parent
            internal set

        var parameters: ListEntry<String, String?>? = null

        private fun getParameter() = parameters ?: kotlin.run {
            ListEntry<String, String?>().also { parameters = it }
        }

        protected fun setParameter(name: String, value: String?) = getParameter().set(name, value)

        protected fun putParameter(name: String, value: String?) = getParameter().put(name, value)

        protected fun getParameter(name: String) = parameters?.getValue(name)

        val path get() = parameters?.let {
                val path = StringBuffer(id)
                path.append("/{${HAS_PARAMETER}}")
                it.forEach { entry ->
                    path.append("/{${entry.key}}")
                }
                path.toString()
            } ?: kotlin.run {
                id
            }

        val query get() = parameters?.let {
                val path = StringBuffer(id)
                path.append("/${HAS_PARAMETER}")
                it.forEach { entry ->
                    path.append("/${entry.value?.toStringHex() ?: PARAMETER_NULL}")
                }
                path.toString()
            } ?: kotlin.run {
                id
            }

        open fun createRoute() = Route(id)

        fun copy(bundle: Bundle?):Route {
            bundle?.takeIf { it.getString(HAS_PARAMETER).isNull }?.let { return this }
            return createRoute().also { route ->
                bundle?.let {
                    parameters?.forEach { entry ->
                        val value = bundle.getString(entry.key).toStringChar() ?: entry.value
                        route.putParameter(entry.key, value)
                    }
                } ?: kotlin.run {
                    parameters?.forEach { entry ->
                        route.putParameter(entry.key, entry.value)
                    }
                }
            }
        }

    }

    abstract class Routes(value: String, val child: Set<Route>) : Route(value) {
        init {
            child.forEach { it.parent = this }
        }
    }

    object Back : Route("back")
    object Finish : Route("finish")
    class RequestFeedback(val target: Route?, val exception:Throwable?) : Route("request_feedback") {
        companion object {
            private val default = RequestFeedback(null, null)
            operator fun invoke() = default
        }
    }
    class NotImplemented(val message: String?) : Route("not_implemented") {
        companion object {
            private val default = NotImplemented(null)
            operator fun invoke() = default
        }
    }

    private val routes: MutableSet<Route> = mutableSetOf(
        Back,
        Finish,
        RequestFeedback(),
        NotImplemented(),
    )

    fun add(route: Route) = routes.add(route)
    fun add(routes: Collection<Route>) = this.routes.addAll(routes)

    fun find(route: String?) = find(route, routes)

    //TODO refactor with tailrec
    private fun find(route: String?, routes: Set<Route>): Route? {
        val iterator = routes.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (route == next.path) {
                return next
            }
            if (next is Routes) {
                find(route, next.child)?.let {
                    return it
                }
            }
        }
        return null

    }
}