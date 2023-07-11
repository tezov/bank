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
package com.tezov.lib_core_kotlin.async.notifier.observer

import com.tezov.lib_core_kotlin.async.notifier.Notifier.Packet
import com.tezov.lib_core_kotlin.async.notifier.Notifier.Subscription
import com.tezov.lib_core_kotlin.extension.ExtensionBoolean.isTrueOrNull
import com.tezov.lib_core_kotlin.type.ref.WR

abstract class Observer<EVENT : Any, PACKET : Packet<EVENT>>(owner: Any, val event: EVENT?) {
    private val ownerWR: WR<Any> = WR(owner)
    var subscription: Subscription? = null
        private set

    var isEnabled = true

    val owner get() = ownerWR.value

    fun bind(subscription: Subscription){
        this.subscription = subscription
    }

    fun unsubscribe() {
        subscription?.let {
            subscription = null
            it.unsubscribe()
        }
    }

    val isCanceled get() = subscription?.let { it.isCanceled || !it.isBound }.isTrueOrNull

    fun notify(packet: PACKET) {
        if (isEnabled) {
            onChanged(packet)
        }

    }

    abstract fun onChanged(packet: PACKET)

}