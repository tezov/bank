



package com.tezov.bank.jUnit

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.tezov.bank.application.ApplicationMock

class JUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, ApplicationMock::class.qualifiedName, context)
    }

}