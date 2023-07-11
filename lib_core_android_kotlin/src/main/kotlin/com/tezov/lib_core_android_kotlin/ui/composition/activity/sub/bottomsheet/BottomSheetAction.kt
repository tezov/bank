/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.composition.activity.sub.bottomsheet

import androidx.compose.runtime.Composable
import com.tezov.lib_core_android_kotlin.ui.compositionTree.activity.sub.ActivitySubAction
import com.tezov.lib_core_kotlin.async.notifier.Event
import com.tezov.lib_core_kotlin.async.notifier.Notifier
import com.tezov.lib_core_kotlin.async.notifier.observable.ObservableEventE
import com.tezov.lib_core_kotlin.async.notifier.observer.event.ObserverEventE
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNull
import com.tezov.lib_core_kotlin.type.ref.Ref.Companion.value
import com.tezov.lib_core_kotlin.type.ref.WR

class BottomSheetAction constructor(
    val state: BottomSheetState
) : ActivitySubAction<BottomSheetState> {

    companion object {
        @Composable
        fun create(
            bottomSheetState: BottomSheetState
        ) = BottomSheetAction(
            state = bottomSheetState,
        )
    }

    private val notifier = Notifier(ObservableEventE<Event, Any?>(), false)
    private var ownerWr: WR<Any>? = null

    //TODO SUGAR SYNTAXE + MOVE register inside interface
    fun register(observer: ObserverEventE<Event, Any?>) =  notifier.register(observer)

    fun unregister(observer: ObserverEventE<Event, Any?>){
        notifier.unregister(observer)
    }

    fun unregisterAll(owner: Any){
        notifier.unregisterAll(owner)
    }

    fun notify(event:Event, value:Any? = null) {
        notifier.obtainPacket(event).also {
            it.value = value
        }
    }

    fun notify(event:Event, value:Any? = null, exception: Throwable?) {
        notifier.obtainPacket(event).also {
            it.set(value, exception)
        }
    }

    private fun updateOwner(owner:Any?){
        ownerWr.value.takeIf { it.isNotNull }?.let {
            if(it != owner){
                notify(Event.OwnerShipLost)
                notifier.unregisterAll(it)
            }
            else{
                return
            }
        }
        ownerWr = owner?.let { WR(owner) }
    }

    fun show(owner:Any, content: @Composable () -> Unit) {
        updateOwner(owner)
        state.show(content)
    }

    fun close() {
        state.show(null)
        notify(Event.Close)
    }

    fun showOnSheetWithOverlay(owner:Any, content: @Composable () -> Unit) {
        show(owner) { BottomSheet.Sheet(content) }
    }

}