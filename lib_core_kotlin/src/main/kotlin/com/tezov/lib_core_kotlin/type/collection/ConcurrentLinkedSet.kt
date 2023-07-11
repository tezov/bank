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
package com.tezov.lib_core_kotlin.type.collection

import java.util.concurrent.ConcurrentLinkedDeque

class ConcurrentLinkedSet<V> : ConcurrentLinkedDeque<V>() {

    override fun add(element: V): Boolean {
        this.forEach {
            if (it == element) return false
        }
        return super.add(element)
    }

    override fun remove(element: V?): Boolean {
        return super.remove(element)
    }

}