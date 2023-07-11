/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

/*
 * ********************************************************************************
 * Created by Tezov under MIT LICENCE.
 * For any request, please send me an email to tezov.app@gmail.com.
 * I'll be glad to answer you if your request is sane :)
 * ********************************************************************************
 */
package com.tezov.lib_core_kotlin.async.notifier.task

import com.tezov.lib_core_kotlin.async.notifier.Notifier
import com.tezov.lib_core_kotlin.async.notifier.NotifierAny
import com.tezov.lib_core_kotlin.async.notifier.ObserverAny
import com.tezov.lib_core_kotlin.async.notifier.observer.Observer
import com.tezov.lib_core_kotlin.type.collection.ListOrObject

open class Task<EVENT : Any, PACKET : Notifier.Packet<EVENT>, OBSERVABLE : Notifier.Observable<EVENT, PACKET>>(
    val event: EVENT?,
    observable: OBSERVABLE
) {
    val notifier = Notifier(observable, true)
    private var subscriptions: ListOrObject<Notifier.Subscription>? = null

    fun obtainScope() = Scope()

    fun obtainPacket() = notifier.obtainPacket(event)

    var isCanceled = false
        private set

    internal fun retainSubscription(subscription: Notifier.Subscription) {
        val subscriptions = this.subscriptions ?: kotlin.run {
            ListOrObject<Notifier.Subscription>().also { subscriptions ->
                this.subscriptions = subscriptions
            }
        }
        subscriptions.add(subscription)
    }

    internal fun disposeSubscription(subscription: Notifier.Subscription) {
        subscriptions?.let {
            it.remove(subscription)
            if (it.isEmpty()) {
                this.subscriptions = null
            }
        }
    }

    fun register(observer: Observer<EVENT, PACKET>): Notifier.Subscription {
        return notifier.register(observer, Subscription(this))
    }

    fun unregister(observer: Observer<EVENT, PACKET>) {
        notifier.unregister(observer)
    }

    internal fun unregisterAll(owner: Any) {
        notifier.unregisterAll(owner)
    }

    internal fun unregisterAll() {
        subscriptions = null
        notifier.unregisterAll()
    }

    fun cancel() {
        isCanceled = true
        subscriptions?.forEach {
            it.cancel()
        }
    }

    fun notifyCanceled() {
        if (!isCanceled) throw java.lang.IllegalStateException("task is not cancel...")
        notifier.dispatchEvent(obtainPacket())
    }

    @Throws(Throwable::class)
    protected open fun finalize() {
        unregisterAll()
    }

    class Subscription(private val task: Task<*, *, *>) : Notifier.Subscription() {

        override var isCanceled: Boolean
            get() = task.isCanceled
            set(value) {
                if (!isCanceled) {
                    task.cancel()
                }
            }

        override fun bind(notifier: NotifierAny, observer: ObserverAny) {
            super.bind(notifier, observer)
            task.retainSubscription(this)
        }

        override fun unbind() {
            task.disposeSubscription(this)
            super.unbind()
        }

    }

    inner class Scope {

        val event get() = this@Task.event

        val isCanceled get() = this@Task.isCanceled

        fun cancel() = this@Task.cancel()

        fun register(observer: Observer<EVENT, PACKET>): Notifier.Subscription =
            this@Task.register(observer)

        fun unregisterAll(owner: Any) = this@Task.unregisterAll(owner)

        fun unregisterAll() = this@Task.unregisterAll()

    }
}