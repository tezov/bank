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
package com.tezov.lib_core_kotlin.async.notifier.observable

import com.tezov.lib_core_kotlin.async.notifier.Notifier

class ObservableValueE<VALUE : Any?> : Observable<Nothing, ObservableValueE.Packet<VALUE>>() {

    override fun createPacket(event: Nothing?): Packet<VALUE> {
        return Packet(newDispatcher())
    }

    class Packet<VALUE : Any?>(
        dispatcher: (packet: Notifier.Packet<*>) -> Unit
    ) : ObservableValue.Packet<VALUE>(dispatcher)
    {

        private var _exception: Throwable? = null

        override val hasPendingEvent get() = super.hasPendingEvent || _exception != null

        override fun updateValue(value: Any?) {
            updateValue(value, null)
        }

        @Suppress("UNCHECKED_CAST")
        fun updateValue(value: Any?, exception: Throwable?) {
            this._exception = exception
            super.updateValue(value)
        }

        var exception: Throwable?
        get() = _exception
        set(value) {
            updateValue(null, value)
        }

        fun set(value: VALUE, exception: Throwable?) {
            updateValue(value, exception)
        }

        fun setIfDifferent(value: VALUE, exception: Throwable?) {
            if (value != this._value && exception !== this._exception) {
                updateValue(value, null)
            }
        }

    }
}