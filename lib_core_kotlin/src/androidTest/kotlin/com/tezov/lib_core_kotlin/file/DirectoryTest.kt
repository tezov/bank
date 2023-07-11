/*
 * Copyright (c) 2023.
 * Created by Tezov on 10/07/2023 20:15
 * Last modified 10/07/2023 19:44
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

/*
 *  ********************************************************************************
 *  Created by Tezov under MIT LICENCE.
 *  For any request, please send me an email to tezov.app@gmail.com.
 *  I'll be glad to answer you if your request is sane :)
 *  ********************************************************************************
 *
 *
 */

package com.tezov.lib_core_kotlin.file

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.application.ApplicationMock
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random
import kotlin.random.nextUBytes

@RunWith(AndroidJUnit4::class)
class DirectoryTest {

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Context>() as ApplicationMock
        application.onCreate()
        StoragePackage.environnement = object : StoragePackage.Environnement {
            override val privateDataPath: String
                get() = ""
            override val privateDataCachePath: String
                get() = application.cacheDir.path
            override val privateDataFilePath: String
                get() = ""
            override val privateDataBasePath: String
                get() = ""
            override val privateSharePreferencePath: String
                get() = ""
            override val publicDataPath: String
                get() = ""
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun can_create() {
        var testNotDone = true
        do {
            val size = Random.nextInt(1, 10)
            val name = Random.nextUBytes(size).toStringHex()
            val directory =
                Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory_$name")
            if (directory.exists) {
                continue
            }
            Truth.assertThat(directory.exists).isFalse()
            Truth.assertThat(directory.create()).isTrue()
            Truth.assertThat(directory.isDirectory).isTrue()
            Truth.assertThat(directory.canRead).isTrue()
            Truth.assertThat(directory.canWrite).isTrue()
            testNotDone = false
        } while (testNotDone)
    }

    @Test
    fun link_isConform() {
        val result = "PRIVATE_DATA_CACHE:BeWNDhxmZvfiyLpFHckBeGNcJRJPrNSDSaPwztUywS"
        val directory =
            Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory_link_isConform")
        if (!directory.exists) {
            directory.create()
        }
        Truth.assertThat(directory.toLinkString()).isEqualTo(result)
    }

    @Test
    fun can_create_from_link() {
        val link = "PRIVATE_DATA_CACHE:BeWNDhxmZvfiyLpFHckBeGNcJRJPrNSDSaPwztUywS"
        val directory = Directory.from(link)
        Truth.assertThat(directory.relativePathString).isEqualTo("test_directory_link_isConform")
        Truth.assertThat(directory.storage!!.name).isEqualTo("PRIVATE_DATA_CACHE")
    }

    @Test
    fun path_isConform() {
        val result = "private_data_cache/test_directory_link_isConform/"
        val directory =
            Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory_link_isConform")
        if (!directory.exists) {
            directory.create()
        }
        Truth.assertThat(directory.toLinkPathString()).isEqualTo(result)
    }


}