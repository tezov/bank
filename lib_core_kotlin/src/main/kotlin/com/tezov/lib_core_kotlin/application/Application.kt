/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.application

import androidx.core.os.ConfigurationCompat
import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tezov.lib_core_java.toolbox.Clock
import java.util.*

open class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        val local: Locale = ConfigurationCompat.getLocales(resources.configuration)[0]!!
        Clock.FormatDate.init(local)
    }

}