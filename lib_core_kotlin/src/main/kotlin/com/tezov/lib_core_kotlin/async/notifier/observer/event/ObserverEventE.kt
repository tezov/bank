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
package com.tezov.lib_core_kotlin.async.notifier.observer.event

import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableEventE
import com.tezov.lib_core_kotlin.async.notifier.observer.Observer

abstract class ObserverEventE<EVENT : Any, VALUE : Any?>(owner: Any, event: EVENT?) :
    Observer<EVENT, ObservableEventE.Packet<EVENT, VALUE>>(owner, event) {

    final override fun onChanged(packet: ObservableEventE.Packet<EVENT, VALUE>) {
        packet.event?.let {event ->
            if (isCanceled) {
                onCancel(event)
            } else {
                packet.exception?.let { exception ->
                    onException(event, packet.value, exception)
                } ?: kotlin.run {
                    onComplete(event, packet.value)
                }
            }
        } ?: run {
            onException(null, packet.value, packet.exception)
        }
    }

    open fun onCancel(event: EVENT) {
        throw NotImplementedError()
    }

    open fun onComplete(event: EVENT, value: VALUE) {
        throw NotImplementedError()
    }

    open fun onException(event: EVENT?, value: VALUE?, exception: Throwable?) {
        throw NotImplementedError()
    }

}