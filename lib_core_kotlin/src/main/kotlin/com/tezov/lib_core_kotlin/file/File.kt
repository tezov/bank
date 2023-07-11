/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_kotlin.buffer.ByteBuffer
import com.tezov.lib_core_kotlin.buffer.ByteBufferBuilder
import com.tezov.lib_core_kotlin.file.Directory.Companion.LINK_SEPARATOR
import com.tezov.lib_core_kotlin.file.Directory.Companion.LINK_VALUE_NULL
import com.tezov.lib_core_kotlin.file.UtilsFile.splitToNameAndExtension
import com.tezov.lib_core_kotlin.file.UtilsFile.splitToPathAndFileName
import com.tezov.lib_core_kotlin.type.primitive.string.StringBase49To.toStringChar
import com.tezov.lib_core_kotlin.type.primitive.string.StringCharTo.toStringBase49
import com.tezov.lib_core_kotlin.util.UtilsString
import com.tezov.lib_core_kotlin.util.UtilsString.appendDateAndTime
import java.io.*
import java.util.*

class File constructor(
    directory: Directory? = null,
    name: String? = null,
    extension: String? = null
) {
    private var _directory: Directory? = directory
    private var _file: FileW? = null
    private var _name: String? = name
    private var _extension: String? = extension
    private var appendDateAndTime = false

    constructor(storage: StoragePackage.Type?) : this(Directory(storage))
    constructor(storage: StoragePackage.Type?, path: StoragePackage.Path?) : this(
        Directory(
            storage,
            path
        )
    )

    constructor(directory: Directory?) : this(directory, null, null) {
        appendDateAndTime = true
    }

    constructor(
        storage: StoragePackage.Type?,
        path: StoragePackage.Path?,
        fileName: String?
    ) : this(
        Directory(storage, path), fileName
    )

    //public StorageFile(UriW uri) throws IOException{
    //    this(uri.getPath());
    //}

    constructor(file: java.io.File) : this(file.path)
    constructor(path: String?) : this(null as Directory?, null, null) {
        val p = path.splitToPathAndFileName()
        if (p.first == null) {
            throw IOException()
        }
        _directory = Directory(p.first)
        if (p.second != null) {
            val p2 = p.second.splitToNameAndExtension()
            _name = p2.first
            _extension = p2.second
        }
    }

    constructor(directory: Directory?, fileName: String?) : this(directory, null, null) {
        val p = fileName.splitToNameAndExtension()
        _name = p.first
        _extension = p.second
    }

    constructor(
        storage: StoragePackage.Type?,
        path: StoragePackage.Path?,
        fileName: String?,
        fileExtension: String?
    ) : this(
        Directory(storage, path), fileName, fileExtension
    )

    val canWrite get() = file.canWrite()

    val canRead get() = file.canRead()

    private fun buildFileName(): String {
        if (name == null) {
            name = generateFileName()
        }
        if (appendDateAndTime) {
            name = appendDateAndTime(name)
        }
        return fullName!!
    }

    private fun generateFileName() = UtilsString.randomBase49(GENERATED_NAME_LENGTH)

    private fun appendDateAndTime(name: String?) = name?.appendDateAndTime()

    fun appendDateAndTime(flag: Boolean) {
        assertFileNotBuilt()
        appendDateAndTime = flag
    }

    val isBuilt: Boolean
        get() = _file != null

    private fun assertFileNotBuilt() {
        if (isBuilt) {
            throw IOException("file already built")
        }
    }

    fun build(create: Boolean): Boolean {
        assertFileNotBuilt()
        _file = FileW(_directory!!.file, buildFileName())
        if (create && !_file!!.exists()) {
            return _file!!.createNewFile()
        }
        return true
    }

    var file: FileW
        get() {
            if (!isBuilt) {
                build(true)
            }
            return _file!!
        }
        set(value) {
            _file = value
        }

    var directory: Directory?
        get() = _directory
        set(value) {
            assertFileNotBuilt()
            _directory = value
        }

    var name: String?
        get() = _name
        set(value) {
            assertFileNotBuilt()
            _name = value
        }

    var extension: String?
        get() = _extension
        set(value) {
            assertFileNotBuilt()
            _extension = value
        }

    val fullName: String?
        get() {
            return _name?.let {
                _extension?.let { _name + DOT_SEPARATOR + _extension } ?: _name
            }
        }


    val path get() = file.path

    val length: Int
        get() {
            if (_file == null) {
                build(false)
            }
            return _file?.length()?.toInt() ?: 0
        }

    val exists: Boolean
        get() {
            if (!isBuilt) {
                build(false)
            }
            return _file?.exists() ?: false
        }

    fun create(): Boolean {
        return if (!isBuilt) {
            build(true)
        } else {
            _file!!.createNewFile()
        }
    }

    fun moveTo(directory: Directory?, overWrite: Boolean): File? {
        return moveTo(File(directory, _name, _extension), overWrite)
    }

    fun moveTo(
        storage: StoragePackage.Type?,
        path: StoragePackage.Path?,
        overWrite: Boolean
    ): File? {
        return moveTo(File(storage, path, _name, _extension), overWrite)
    }

    fun moveTo(target: File, overWrite: Boolean): File? {
        val targetFile = target.file
        if (targetFile.exists()) {
            if (!overWrite) {
                return null
            }
            if (!targetFile.delete()) {
                return null
            }
        }
        return if (file.renameTo(targetFile)) {
            target
        } else {
            null
        }
    }

    fun delete(): Boolean {
        return file.delete()
    }

    protected fun toByteBuffer(): ByteBufferBuilder {
        val byteBuffer: ByteBufferBuilder = ByteBufferBuilder.obtain()
        byteBuffer.bytes = _directory?.toUByteArray()
        byteBuffer.string = (_name)
        byteBuffer.string = (_extension)
        return byteBuffer
    }

    fun toUByteArray(): UByteArray {
        return toByteBuffer().build().uByteArray
    }

    protected fun fromByteBuffer(byteBuffer: ByteBuffer) {
        val directoryBytes = byteBuffer.bytes
        _directory = directoryBytes?.let { Directory.from(it) }
        _name = byteBuffer.string
        _extension = byteBuffer.string
    }

    protected fun fromUByteArray(bytes: UByteArray): File {
        fromByteBuffer(ByteBuffer.wrap(bytes))
        return this
    }

    protected fun fromStringIterator(it: Iterator<String>): File? {
        return fromStringIterator(this, it)
    }

    protected fun fromString(link: String): File {
        fromStringIterator(
            Arrays.asList(*link.split(LINK_SEPARATOR).toTypedArray()).iterator()
        )
        return this
    }

    fun toLinkString(): String {
        return toLinkString(directory, name, extension)
    }

    fun toLinkPathString(): String {
        val data = StringBuilder()
        _directory?.let {
            data.append(it.toLinkPathString())
        }
        fullName?.let {
            data.append(it)
        }
        return data.toString()
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is File) {
            false
        } else toUByteArray().asByteArray().contentEquals(other.toUByteArray().asByteArray())
    }

    @get:Throws(FileNotFoundException::class)
    val outputStream: FileOutputStream
        get() = FileOutputStream(file)

    @get:Throws(IOException::class)
    val writer: FileWriter
        get() = FileWriter(file)

    @get:Throws(FileNotFoundException::class)
    val inputStream: FileInputStream
        get() = FileInputStream(file)

    @get:Throws(IOException::class)
    val reader: FileReader
        get() = FileReader(file)

    companion object {
        const val GENERATED_NAME_LENGTH = 16
        const val LINK_DIRECTORY = "@"
        const val DOT_SEPARATOR = "."
        fun from(bytes: UByteArray) = File().fromUByteArray(bytes)
        fun from(link: String) = File().fromString(link)
        protected fun from(it: Iterator<String>) = File().fromStringIterator(it)

        fun toLinkString(directoryLink: String?, fileName: String?) =
            fileName.splitToNameAndExtension()
                .let { toLinkString(directoryLink, it.first, it.second) }

        fun toLinkString(directory: Directory?, fileName: String?) =
            fileName.splitToNameAndExtension().let { toLinkString(directory, it.first, it.second) }

        fun toLinkString(
            directory: Directory?,
            name: String?,
            extension: String?
        ) = toLinkString(directory?.toLinkString(), name, extension)

        fun toLinkString(directoryLink: String?, name: String?, extension: String?): String {
            val data = StringBuilder()
            directoryLink?.let {
                data.append(LINK_DIRECTORY).append(LINK_SEPARATOR)
                data.append(it).append(LINK_SEPARATOR)
            } ?: let {
                data.append(LINK_VALUE_NULL).append(LINK_SEPARATOR)
            }
            data.append(if (name != null) name.toStringBase49() else LINK_VALUE_NULL)
                .append(LINK_SEPARATOR)
            data.append(if (extension != null) extension.toStringBase49() else LINK_VALUE_NULL)
            return data.toString()
        }

        fun consumeIterator(it: Iterator<String>) {
            fromStringIterator(null, it)
        }

        fun getFullName(link: String): String? {
            val it: Iterator<String> =
                listOf(*link.split(LINK_SEPARATOR).toTypedArray()).iterator()
            val name = getName(it)
            val extension = getExtension(it)
            return extension?.let { name + DOT_SEPARATOR + extension } ?: name
        }

        fun getExtension(link: String): String? {
            val it: Iterator<String> =
                listOf(*link.split(LINK_SEPARATOR).toTypedArray()).iterator()
            getName(it)
            return getExtension(it)
        }

        fun getName(link: String) =
            getName(listOf(*link.split(LINK_SEPARATOR).toTypedArray()).iterator())

        private fun getName(it: Iterator<String>): String? {
            val directoryString = it.next()
            if (directoryString == LINK_DIRECTORY) {
                Directory.consumeIterator(it)
            }
            var name: String? = it.next()
            if (LINK_VALUE_NULL == name) {
                name = null
            }
            return name
        }

        private fun getExtension(it: Iterator<String>): String? {
            var extension: String? = it.next()
            if (LINK_VALUE_NULL == extension) {
                extension = null
            }
            return extension
        }

        private fun fromStringIterator(file: File?, iterator: Iterator<String>): File? {
            file?.let {
                val directoryString = iterator.next()
                if (LINK_DIRECTORY == directoryString) {
                    file._directory = Directory().fromStringIterator(iterator)
                } else {
                    file._directory = null
                }
                file._name = iterator.next()
                if (LINK_VALUE_NULL == file._name) {
                    file._name = null
                } else {
                    file._name = file._name.toStringChar()
                }
                file._extension = iterator.next()
                if (LINK_VALUE_NULL == file._extension) {
                    file._extension = null
                } else {
                    file._extension = file._extension.toStringChar()
                }
            } ?: let {
                val directoryString = iterator.next()
                if (directoryString == LINK_DIRECTORY) {
                    Directory.consumeIterator(iterator)
                }
                iterator.next()
                iterator.next()
            }
            return file
        }
    }
}