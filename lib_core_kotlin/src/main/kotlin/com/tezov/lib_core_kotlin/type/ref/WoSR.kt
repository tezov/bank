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
package com.tezov.lib_core_kotlin.type.ref

import com.tezov.lib_core_kotlin.util.UtilsNull.ifNotNull
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

//Weak or Strong
class WoSR<T:Any> (ref: T? = null, keepAsWeakRef: Boolean = true, private var q: ReferenceQueue<T>? = null): Ref<T>() {
    private var ref: Any? = null

    @Suppress("UNCHECKED_CAST")
    override var value: T?
        get() = ((ref as? WeakReference<*>)?.get() ?: ref) as T?
        set(value) {
            throw UnsupportedOperationException()
        }

    init {
        value(ref, keepAsWeakRef, q)
    }

    val isWeak get() = ref is WeakReference<*>

    val isStrong get() = ref != null && ref !is WeakReference<*>

    fun makeWeak() {
        if(isStrong){
            value?.let { currentRef ->
                ref = q?.let {
                    LostRef(currentRef, q)
                } ?: run {
                    WeakReference(currentRef)
                }
            }
        }
    }

    fun makeStrong() {
        if(isWeak){
            value?.let { currentRef ->
                ref = currentRef
            }
        }
    }

    fun value(ref: T?, keepAsWeakRef: Boolean = true, q: ReferenceQueue<T>? = null) {
        updateInfo(ref)
        this.q = q
        this.ref = ref
        if(keepAsWeakRef){
            makeWeak()
        }
    }

}