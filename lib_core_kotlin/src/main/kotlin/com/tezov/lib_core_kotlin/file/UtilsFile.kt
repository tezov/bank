/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_java.type.unit.UnitByte
import com.tezov.lib_core_kotlin.extension.ExtensionList.NULL_INDEX
import com.tezov.lib_core_kotlin.extension.ExtensionList.isNotNullIndex
import com.tezov.lib_core_kotlin.extension.ExtensionNull.nullify
import com.tezov.lib_core_kotlin.file.UtilsStream.closeSilently
import com.tezov.lib_core_kotlin.type.primaire.Pair
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toLong
import com.tezov.lib_core_kotlin.type.primitive.LongTo
import com.tezov.lib_core_kotlin.type.primitive.LongTo.toUByteArray
import com.tezov.lib_core_kotlin.util.UtilsBytes
import java.io.*
import java.net.URLConnection
import java.util.regex.Pattern
import java.util.zip.CRC32

object UtilsFile {
    val DEFAULT_BUFFER_SIZE = UnitByte.o.convert(20, UnitByte.Mo).toInt()

    const val MINE_TYPE_ALL = "*/*"
    const val MINE_TYPE_DIRECTORY = "resource/folder"
    const val MINE_TYPE_OCTET_STREAM = "application/octet-stream"
    const val MINE_TYPE_PLAIN_TEXT = "text/plain"

    @Throws(IOException::class)
    fun transfer(source: java.io.File, destination: File) {
        transfer(FileInputStream(source), destination.outputStream)
    }

    @Throws(IOException::class)
    fun transfer(source: InputStream, destination: File) {
        transfer(source, destination.outputStream)
    }

    @Throws(IOException::class)
    fun transfer(source: File, destination: java.io.File) {
        transfer(source.inputStream, FileOutputStream(destination))
    }

    @Throws(IOException::class)
    fun transfer(source: File, destination: OutputStream) {
        transfer(source.inputStream, destination)
    }

    @Throws(IOException::class)
    fun transfer(source: File, destination: File) {
        transfer(source.inputStream, destination.outputStream)
    }

    @Throws(IOException::class)
    fun transfer(source: InputStream, destination: java.io.File) {
        transfer(source, FileOutputStream(destination))
    }

    @Throws(IOException::class)
    fun transfer(source: java.io.File, destination: OutputStream) {
        transfer(FileInputStream(source), destination)
    }

    @Throws(IOException::class)
    fun transfer(source: File, destination: Directory) {
        transfer(source.inputStream, File(destination, source.fullName).outputStream)
    }

    @Throws(IOException::class)
    fun transfer(source: java.io.File, destination: java.io.File) {
        transfer(FileInputStream(source), FileOutputStream(destination))
    }

    @Throws(IOException::class)
    fun transfer(source: InputStream, destination: OutputStream) {
        try {
            copy(source, destination)
            destination.close()
            source.close()
        } catch (e: IOException) {
            closeSilently(destination)
            closeSilently(source)
            throw e
        }
    }

    @Throws(IOException::class)
    fun copy(
        source: InputStream,
        destination: OutputStream,
        available: Int? = null,
        bufferSize: Int = DEFAULT_BUFFER_SIZE
    ) {
        var _available = available
        if (_available == null) {
            var length: Int
            val buffer = UtilsBytes.obtain(bufferSize).asByteArray()
            while (source.read(buffer).also { length = it } > 0) {
                destination.write(buffer, 0, length)
            }
        } else {
            var length: Int
            val buffer: ByteArray = UtilsBytes.obtain(bufferSize).asByteArray()
            while (source.read(buffer, 0, Math.min(_available, bufferSize))
                    .also { length = it } > 0
            ) {
                destination.write(buffer, 0, length)
                _available -= length
            }
        }
    }

    fun String?.shortenName(maxLength: Int) = this?.let {
        it.truncateName(it.length - maxLength)
    } ?: this

    fun String?.truncateName(lengthToRemove: Int) =
        this?.takeIf { it.isNotBlank() && lengthToRemove > 0 && lengthToRemove < it.length }
            ?.let {
                FileName(it).apply {
                    truncateNameOf(lengthToRemove)
                }.fullName
            } ?: this

    fun String?.mimeTypeForFullName(): String? = this?.let {
        URLConnection.getFileNameMap().run {
            getContentTypeFor(it) ?: MINE_TYPE_OCTET_STREAM
        }
    }

    fun String?.extension(): String? = this?.let { name ->
        name.lastIndexOf(File.DOT_SEPARATOR).takeIf { it.isNotNullIndex }?.let { index ->
            name.substring(index + 1).nullify()
        }
    }

    fun String?.name(): String? = this?.let { name ->
        name.lastIndexOf(File.DOT_SEPARATOR).takeIf { it.isNotNullIndex }?.let { index ->
            name.substring(0, index).nullify()
        } ?: this
    }


    fun String?.splitToNameAndExtension() = this?.let {
        val name: String?
        val extension: String?
        val dotIndex = it.lastIndexOf(File.DOT_SEPARATOR)
        if (dotIndex.isNotNullIndex) {
            extension = it.substring(dotIndex + 1)
            name = it.substring(0, dotIndex)
        } else {
            extension = null
            name = it
        }
        Pair(name.nullify(), extension.nullify())
    } ?: Pair(null, null)

    fun String?.splitToPathAndFileName() = this?.let {
        val index = it.lastIndexOf(Directory.PATH_SEPARATOR)
        if (index.isNotNullIndex) {
            val path = it.substring(0, index + 1)
            val name = it.substring(index + 1)
            Pair(path.nullify(), name.nullify())
        } else {
            Pair(null, it.nullify())
        }
    } ?: Pair(null, null)

    class DigesterCRC32(private val digester: CRC32 = CRC32()) {

        fun update(b: Byte) {
            digester.update(b.toInt())
        }

        fun update(bytes: ByteArray) {
            digester.update(bytes, 0, bytes.size)
        }

        fun update(bytes: ByteArray?, off: Int, len: Int) {
            digester.update(bytes, off, len)
        }

        fun reset() {
            digester.reset()
        }

        val value: Long
            get() = digester.value
        val valueByte: UByteArray
            get() = digester.value.toUByteArray()

        fun equals(value: UByteArray?) = this.value == value.toLong()

        companion object {
            fun length() = LongTo.BYTES
        }

    }

    class FileName(fileFullName: String) {
        var shortName: String? = null
            private set
        var number: Int? = null
        var extension: String? = null

        init {
            val matcher = pattern.matcher(fileFullName)
            if (matcher.find()) {
                shortName = matcher.group(1)
                val number = matcher.group(2)
                if (number != null) {
                    this.number = number.toInt()
                }
                extension = matcher.group(3)
            } else {
                val p = fileFullName.splitToNameAndExtension()
                shortName = p.first
                extension = p.second
            }
        }

        val fullName get() = addNumber(shortName!!, extension, number)
        fun getFullName(number: Int?): String? {
            return addNumber(shortName!!, extension, number)
        }

        val fullNameWithoutNumber: String?
            get() = addNumber(shortName!!, extension, null)

        val name get() = addNumber(shortName!!, null, number)
        fun getName(number: Int?): String? {
            return addNumber(shortName!!, null, number)
        }

        val nameWithoutNumber: String?
            get() = addNumber(shortName!!, null, null)

        fun setName(name: String?): FileName {
            shortName = name
            return this
        }

        fun appendToName(s: String): FileName {
            shortName += s
            return this
        }

        fun incNumber() = number?.inc()

        fun truncateNameOf(length: Int) {
            if (length > 1 && shortName!!.length > length) {
                shortName = shortName!!.substring(0, shortName!!.length - length) + "~"
            }
        }

        val queryPattern: String
            get() {
                var pattern = shortName + "(%)"
                if (extension != null) {
                    pattern += ".$extension"
                }
                return pattern
            }

        companion object {
            private val pattern = Pattern.compile("^(.*?)(?:\\((\\d+)\\))?(?:\\.([^.]*))?$")
            fun String?.number(): Int? {
                this?.let {
                    try {
                        val pattern = Pattern.compile("^(?:.*?)(?:\\((\\d+)\\))?(?:\\.[^.]*)?$")
                        val matcher = pattern.matcher(it)
                        if (matcher.find()) {
                            val number = matcher.group(1)
                            if (number != null) {
                                return number.toInt()
                            }
                        }
                    } catch (e: Throwable) {
                    }
                }
                return null
            }

            fun String?.addNumber(number: Int?): String? {
                val p = this.splitToNameAndExtension()
                return addNumber(p.first, p.second, number)
            }

            fun addNumber(fileName: String?, extension: String?, number: Int?): String? {
                var fileNameWithNumber = fileName
                fileNameWithNumber?.let {
                    number?.let { fileNameWithNumber += "($number)" }
                    extension?.let { fileNameWithNumber += File.DOT_SEPARATOR + extension }
                }
                return fileNameWithNumber
            }
        }


    }
}