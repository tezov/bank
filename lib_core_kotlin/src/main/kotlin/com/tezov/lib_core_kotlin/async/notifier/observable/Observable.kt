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
import com.tezov.lib_core_kotlin.async.notifier.Notifier.Packet
import com.tezov.lib_core_kotlin.async.notifier.NotifierAny
import com.tezov.lib_core_kotlin.type.collection.ListOrObject
import com.tezov.lib_core_kotlin.type.ref.Ref.Companion.value
import com.tezov.lib_core_kotlin.type.ref.WR

abstract class Observable<EVENT : Any, PACKET : Packet<EVENT>> : Notifier.Observable<EVENT, PACKET> {
    private val accessList = ListOrObject<PACKET>()
    private var notifierWR: WR<NotifierAny>? = null

    @Suppress("UNCHECKED_CAST")
    internal fun newDispatcher(): (packet: Packet<*>) -> Unit = {
        notifierWR.value?.dispatchEvent(it as Packet<Any>)
    }

    override fun attachTo(notifier: NotifierAny) {
        notifierWR = WR(notifier)
    }

    override fun isPacketValid(packet: PACKET): Boolean =
        synchronized(this) { getPacket(packet.event) == packet }

    override fun getPacket(event: EVENT?): PACKET? = synchronized(this) {
        accessList.get()?.takeIf {
            it.event == event
        }
    }

    override fun obtainPacket(event: EVENT?): PACKET = synchronized(this) {
        accessList.get()?.takeIf {
            it.event == event
        } ?: createPacket(event).also {
            accessList.set(it)
        }
    }

    override fun getPacketList(): List<PACKET> =
        synchronized(this) { accessList }

    override fun clearPacket() = synchronized(this) { accessList.clear() }

    protected abstract fun createPacket(event: EVENT?): PACKET

}