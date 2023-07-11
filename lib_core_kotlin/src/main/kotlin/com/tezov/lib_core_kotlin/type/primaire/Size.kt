/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.type.primaire

class Size (var width: Int = 0, var height: Int = 0) {

    fun swap() {
        val tmp = width
        width = height
        height = tmp
    }

    val ratio
        get() = if (width != 0) {
            height.toFloat() / width.toFloat()
        } else {
            Float.NaN
        }

    fun scaleTo(s: Scale) {
        width = (width * s.w).toInt()
        height = (height * s.h).toInt()
    }

    fun scaleFrom(s: Scale) {
        width = (width / s.w).toInt()
        height = (height / s.h).toInt()
    }

    companion object {
        fun wrap(size: android.util.Size?) = size?.let { Size(it.width, it.height) }
    }
}