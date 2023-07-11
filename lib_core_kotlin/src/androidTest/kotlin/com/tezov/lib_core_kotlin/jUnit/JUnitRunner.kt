/*
 *  ********************************************************************************
 *  Created by Tezov under MIT LICENCE.
 *  For any request, please send me an email to tezov.app@gmail.com.
 *  I'll be glad to answer you if your request is sane :)
 *  ********************************************************************************
 *
 *
 */

package com.tezov.lib_core_kotlin.jUnit

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.tezov.lib_core_kotlin.application.ApplicationMock

class JUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, ApplicationMock::class.qualifiedName, context)
    }
}