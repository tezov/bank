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

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

//Weak
class WR<T : Any>(ref: T? = null, q: ReferenceQueue<T>? = null) : Ref<T>() {
    private var ref: WeakReference<T>? = null

    override var value: T?
        get() = ref?.get()
        set(value) {
            value(value, null)
        }

    init {
        value(ref, q)
    }

    fun value(ref: T?, q: ReferenceQueue<T>? = null) {
        updateInfo(ref)
        this.ref = ref?.let {
            q?.let {
                LostRef(ref, q)
            } ?: run {
                WeakReference(ref)
            }
        }
    }

}