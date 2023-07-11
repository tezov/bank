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
import com.tezov.lib_core_kotlin.util.UtilsNull.NULL


open class ObservableValue<VALUE:Any?> : Observable<Nothing, ObservableValue.Packet<VALUE>>() {

    override fun createPacket(event: Nothing?): Packet<VALUE> {
        return Packet(newDispatcher())
    }

    open class Packet<VALUE:Any?> (
        val dispatcher: (packet: Notifier.Packet<*>) -> Unit
    ): Notifier.Packet<Nothing> {

        override val event: Nothing? get() = null

        protected var _value: Any? = NULL

        override val hasPendingEvent get() = _value != NULL

        @Suppress("UNCHECKED_CAST")
        protected open fun updateValue(value: Any?) {
            this._value = value as? VALUE
            dispatcher(this)
        }

        @Suppress("UNCHECKED_CAST")
        open var value: VALUE
            get() = _value?.takeIf { it != NULL } as VALUE
            set(value) {
                updateValue(value)
            }

        @Suppress("UNCHECKED_CAST")
        open var valueIfDifferent: VALUE
            get() = value
            set(value) {
                if(value != this._value){
                    updateValue(value)
                }
            }
    }
}