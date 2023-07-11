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
import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.application.ApplicationMock
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.random.nextUBytes

@RunWith(AndroidJUnit4::class)
class FileTest {

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
    fun can_generate_file_name() {
        val directory = Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory")
        if (!directory.exists) {
            directory.create()
        }
        val file = File(directory)
        file.build(false)
        val pattern = Pattern.compile("^[a-zA-Z]+_\\d{8}_\\d{6}$")
        val matcher = pattern.matcher(file.name)
        Truth.assertThat(matcher.matches()).isTrue()
    }

    @Test
    fun can_create() {
        var testNotDone = true
        do {
            val size = Random.nextInt(1, 10)
            val name = Random.nextUBytes(size).toStringHex()
            val directory = Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory")
            if (!directory.exists) {
                directory.create()
            }
            val file = File(directory, name)
            if (file.exists) {
                continue
            }
            Truth.assertThat(file.exists).isFalse()
            Truth.assertThat(file.create()).isTrue()
            Truth.assertThat(file.file!!.isDirectory).isFalse()
            Truth.assertThat(file.canRead).isTrue()
            Truth.assertThat(file.canWrite).isTrue()
            testNotDone = false
        } while (testNotDone)
    }

    @Test
    fun link_isConform_no_extension() {
        val result = "@:PRIVATE_DATA_CACHE:UJPzPaJHMmbssLJopHMp:SwLanVZFDKwFTnnQiTKN:#"
        val directory = Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory")
        if (!directory.exists) {
            directory.create()
        }
        val file = File(directory, "link_isConform")
        if (!file.exists) {
            file.create()
        }
        Truth.assertThat(file.toLinkString()).isEqualTo(result)
    }

    @Test
    fun can_create_from_link_no_extension() {
        val link = "@:PRIVATE_DATA_CACHE:UJPzPaJHMmbssLJopHMp:SwLanVZFDKwFTnnQiTKN:#"
        val file = File.from(link)
        Truth.assertThat(file.directory!!.relativePathString).isEqualTo("test_directory")
        Truth.assertThat(file.directory!!.storage!!.name).isEqualTo("PRIVATE_DATA_CACHE")
        Truth.assertThat(file.name).isEqualTo("link_isConform")
    }

    @Test
    fun link_isConform_with_extension() {
        val result = "@:PRIVATE_DATA_CACHE:UJPzPaJHMmbssLJopHMp:GXayCq:LHbZNUFhgnzaz"
        val directory = Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory")
        if (!directory.exists) {
            directory.create()
        }
        val file = File(directory, "link.isConform")
        if (!file.exists) {
            file.create()
        }
        Truth.assertThat(file.toLinkString()).isEqualTo(result)
    }

    @Test
    fun can_create_from_link_with_extension() {
        val link = "@:PRIVATE_DATA_CACHE:UJPzPaJHMmbssLJopHMp:GXayCq:LHbZNUFhgnzaz"
        val file = File.from(link)
        Truth.assertThat(file.directory!!.relativePathString).isEqualTo("test_directory")
        Truth.assertThat(file.directory!!.storage!!.name).isEqualTo("PRIVATE_DATA_CACHE")
        Truth.assertThat(file.name).isEqualTo("link")
        Truth.assertThat(file.extension).isEqualTo("isConform")
    }

    @Test
    fun path_isConform() {
        val result = "private_data_cache/test_directory/link_isConform"
        val directory = Directory(StoragePackage.Type.PRIVATE_DATA_CACHE, "test_directory")
        if (!directory.exists) {
            directory.create()
        }
        val file = File(directory, "link_isConform")
        if (!file.exists) {
            file.create()
        }
        Truth.assertThat(file.toLinkPathString()).isEqualTo(result)
    }

}