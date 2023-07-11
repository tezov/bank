/*
 *  ********************************************************************************
 *  Created by Tezov under MIT LICENCE.
 *  For any request, please send me an email to tezov.app@gmail.com.
 *  I'll be glad to answer you if your request is sane :)
 *  Last update 2023
 *  ********************************************************************************
 */

package com.tezov.lib_core_kotlin.file

import com.google.common.truth.Truth
import com.tezov.lib_core_kotlin.file.UtilsFile.extension
import com.tezov.lib_core_kotlin.file.UtilsFile.name
import com.tezov.lib_core_kotlin.file.UtilsFile.splitToNameAndExtension
import com.tezov.lib_core_kotlin.type.primaire.Pair
import org.junit.After
import org.junit.Before
import org.junit.Test

class UtilsFileTest {


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun can_find_extension_simple() {
        val extension = "extension"
        val fileFullName = "name" + File.DOT_SEPARATOR + extension
        Truth.assertThat(fileFullName.extension()).isEqualTo(extension)
    }

    @Test
    fun can_find_extension_double() {
        val extension = "extension"
        val fileFullName = "name.save" + File.DOT_SEPARATOR + extension
        Truth.assertThat(fileFullName.extension()).isEqualTo(extension)
    }

    @Test
    fun can_not_find_extension() {
        val fileFullName = "name"
        Truth.assertThat(fileFullName.extension()).isEqualTo(null)
    }

    @Test
    fun can_find_name_simple() {
        val name = "name"
        val fileFullName = name + File.DOT_SEPARATOR + "extension"
        Truth.assertThat(fileFullName.name()).isEqualTo(name)
    }

    @Test
    fun can_find_name_complexe() {
        val name = "name.name2"
        val fileFullName = name + File.DOT_SEPARATOR + "extension"
        Truth.assertThat(fileFullName.name()).isEqualTo(name)
    }

    @Test
    fun can_find_name_and_extension_simple() {
        val name = "name"
        val extension = "extension"
        val fileFullName = name + File.DOT_SEPARATOR + extension
        Truth.assertThat(fileFullName.splitToNameAndExtension()).isEqualTo(Pair(name, extension))
    }

    @Test
    fun can_find_name_and_extension_complex() {
        val name = "name.name2.name3"
        val extension = "extension"
        val fileFullName = name + File.DOT_SEPARATOR + extension
        Truth.assertThat(fileFullName.splitToNameAndExtension()).isEqualTo(Pair(name, extension))
    }

}