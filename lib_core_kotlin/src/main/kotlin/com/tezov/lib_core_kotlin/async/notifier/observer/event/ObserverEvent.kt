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

import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableEvent
import com.tezov.lib_core_kotlin.async.notifier.observer.Observer

abstract class ObserverEvent<EVENT : Any, VALUE : Any?>(owner: Any, event: EVENT?) :
    Observer<EVENT, ObservableEvent.Packet<EVENT, VALUE>>(owner, event) {

    final override fun onChanged(packet: ObservableEvent.Packet<EVENT, VALUE>) {
        packet.event?.let { event ->
            if (isCanceled) {
                onCancel(event)
            } else {
                onComplete(event, packet.value)
            }
        } ?: run {
            throw IllegalStateException("can't be possible")
        }

    }

    open fun onCancel(event: EVENT) {
        throw NotImplementedError()
    }

    open fun onComplete(event: EVENT, value: VALUE) {
        throw NotImplementedError()
    }
}