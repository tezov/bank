/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_java.toolbox.Clock
import com.tezov.lib_core_kotlin.application.AppRandomNumber
import com.tezov.lib_core_kotlin.buffer.ByteBuffer
import com.tezov.lib_core_kotlin.buffer.ByteBufferBuilder
import com.tezov.lib_core_kotlin.type.primitive.string.StringBase49To.toStringChar
import com.tezov.lib_core_kotlin.type.primitive.string.StringCharTo.toStringBase49
import java.io.File
import java.io.FilenameFilter
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class Directory {
    private var _file: FileW? = null
    private var _storage: StoragePackage.Type? = null
    private var _relativePath: StoragePackage.Path? = null
    private var _relativePathString: String? = null

    constructor() {
        init(null, null, null)
    }

    constructor(storage: StoragePackage.Type?) {
        init(storage, null, null)
    }

    constructor(storage: StoragePackage.Type?, relativePath: String?) {
        init(storage, null, relativePath)
    }

    constructor(path: String?) {
        init(null, null, path)
    }

    constructor(directory: Directory, pathDirectory: Directory) : this(
        directory, pathDirectory.relativePathString
    )

    constructor(directory: Directory, pathDirectory: StoragePackage.Path) : this(
        directory, pathDirectory.value
    )

    constructor(directory: Directory, pathDirectory: String?) {
        init(directory._storage, null, null)
        val relativePath = directory.relativePathString
        val pathString = StringBuilder()
        if (relativePath != null) {
            pathString.append(relativePath)
        }
        pathString.append(pathDirectory)
        relativePathString = pathString.toString()
    }

    constructor(storage: StoragePackage.Type?, relativePath: StoragePackage.Path?) {
        init(storage, relativePath, null)
    }

    constructor(
        storage: StoragePackage.Type?,
        relativePath: StoragePackage.Path,
        subPath: String
    ) {
        init(storage, null, relativePath.value + subPath)
    }

    private fun init(
        storage: StoragePackage.Type?,
        path: StoragePackage.Path?,
        pathString: String?
    ) {
        _storage = storage
        _relativePath = path
        _relativePathString = pathString
    }

    val isBuilt = _file != null

    private fun assertFileNotBuilt() {
        if (isBuilt) {
            throw IOException("directory already built")
        }
    }

    fun build(create: Boolean): Boolean {
        assertFileNotBuilt()
        val storageDirectory = _storage?.newFileW()
        _file = when {
            _relativePath != null -> {
                FileW(storageDirectory, _relativePath!!.value)
            }
            _relativePathString != null -> {
                FileW(storageDirectory, _relativePathString!!)
            }
            else -> {
                storageDirectory
            }
        }
        if (create) {
            return _file!!.createDirectory()
        }
        return true
    }

    val isDirectory get() = file.isDirectory
    val canWrite get() = getFile(false).canWrite()
    val canRead get() = getFile(false).canRead()

    var file
        get() = getFile(true)
        set(value) {
            _file = value
        }

    fun getFile(create: Boolean): FileW {
        if (!isBuilt) {
            build(create)
        }
        return _file!!
    }

    var storage
        get() = _storage
        set(value) {
            assertFileNotBuilt()
            _storage = value
        }

    val hasRelativePath get() = _relativePath != null || _relativePathString != null

    var relativePath
        get() = _relativePath
        set(value) {
            assertFileNotBuilt()
            _relativePath = value
            _relativePathString = null
        }

    var relativePathString
        get() = _relativePath?.value ?: _relativePathString
        set(value) {
            assertFileNotBuilt()
            _relativePathString = value
            _relativePath = null
        }

    val path: String
        get() = file.path

    fun delete(): Int {
        return deleteFiles(true, true)
    }

    fun deleteFiles(recursive: Boolean, deleteDirectoryIfEmpty: Boolean = true): Int {
        if (!isDirectory) return 0
        var count = 0
        file.let { file ->
            val children = file.list()
            if (children != null) {
                for (childName in children) {
                    val child = File(file, childName)
                    if (child.isDirectory) {
                        if (recursive) {
                            count += Directory(this, PATH_SEPARATOR + childName).deleteFiles(
                                true,
                                deleteDirectoryIfEmpty
                            )
                        }
                    } else if (child.delete()) {
                        count++
                    }
                }
            }
            if (deleteDirectoryIfEmpty && isEmpty) {
                file.delete()
            }
        }
        return count
    }

    fun deleteFiles(
        expiredDelay: Long,
        unit: TimeUnit?,
        recursive: Boolean,
        deleteDirectoryIfEmpty: Boolean
    ): Int {
        return deleteFiles(
            TimeUnit.MILLISECONDS.convert(expiredDelay, unit),
            recursive,
            deleteDirectoryIfEmpty
        )
    }

    fun deleteFiles(
        expiredDelay_ms: Long,
        recursive: Boolean,
        deleteDirectoryIfEmpty: Boolean
    ): Int {
        return deleteFiles(
            Clock.MilliSecond.now(),
            expiredDelay_ms,
            recursive,
            deleteDirectoryIfEmpty
        )
    }

    private fun deleteFiles(
        now: Long,
        expiredDelay_ms: Long,
        recursive: Boolean,
        deleteDirectoryIfEmpty: Boolean
    ): Int {
        if (!isDirectory) return 0
        var count = 0
        file.let{ file ->
            val children = file.list()
            if (children != null) {
                for (childName in children) {
                    val child = File(file, childName)
                    if (child.isDirectory) {
                        if (recursive) {
                            count += Directory(this, PATH_SEPARATOR + childName).deleteFiles(
                                now,
                                expiredDelay_ms,
                                true,
                                deleteDirectoryIfEmpty
                            )
                        }
                    } else {
                        val expired = file.lastModified() + expiredDelay_ms
                        if (expired < now) {
                            if (child.delete()) {
                                count++
                            }
                        }
                    }
                }
            }
            if (deleteDirectoryIfEmpty && isEmpty) {
                file.delete()
            }
        }
        return count
    }

    val exists get() = getFile(false).exists()

    val isEmpty get() = file.list()!!.isEmpty()

    fun create(): Boolean {
        return if (!isBuilt) {
            return build(true)
        } else {
            _file!!.createDirectory()
        }
    }

    val list get() = file.list()!!

    fun list(filter: FilenameFilter?) = file.list(filter)

    val listFiles get() = file.listFiles()

    protected fun toByteBuffer(): ByteBufferBuilder {
        val byteBuffer = ByteBufferBuilder.obtain()
        byteBuffer.string = _storage?.name
        if (_relativePath != null) {
            byteBuffer.string = (_relativePath!!.name)
        } else if (_relativePathString != null) {
            byteBuffer.string = (_relativePathString)
        } else {
            byteBuffer.string = null
        }
        return byteBuffer
    }

    fun toUByteArray(): UByteArray {
        return toByteBuffer().build().uByteArray
    }

    protected fun fromByteBuffer(byteBuffer: ByteBuffer) {
        val storageString = byteBuffer.string
        _storage = StoragePackage.Type.valueOf(storageString!!)
        val pathString = byteBuffer.string
        _relativePath = null
        _relativePathString = null
        if (pathString != null && pathString != LINK_VALUE_NULL) {
            _relativePath = StoragePackage.Path.valueOf(pathString)
            if (_relativePath == null) {
                _relativePathString = pathString
            }
        }
    }

    protected fun fromUByteArray(bytes: UByteArray): Directory {
        fromByteBuffer(ByteBuffer.wrap(bytes))
        return this
    }

    fun fromStringIterator(it: Iterator<String>): Directory? {
        return fromStringIterator(this, it)
    }

    protected fun fromString(link: String): Directory {
        fromStringIterator(Arrays.asList(*link.split(LINK_SEPARATOR).toTypedArray()).iterator())
        return this
    }

    fun toLinkString(): String? {
        return toLinkString(_storage?.name, relativePathString)
    }

    fun toLinkPathString(): String {
        val data = StringBuilder()
        _storage?.let {
            data.append(it.name.lowercase(Locale.getDefault())).append(PATH_SEPARATOR)
        }
        if (_relativePath != null) {
            data.append(_relativePath!!.name).append(PATH_SEPARATOR)
        } else if (_relativePathString != null) {
            data.append(_relativePathString).append(PATH_SEPARATOR)
        }
        return data.toString()
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is Directory) {
            false
        } else Arrays.equals(toUByteArray().asByteArray(), other.toUByteArray().asByteArray())
    }

    companion object {
        val PATH_SEPARATOR = File.separator
        const val LINK_SEPARATOR = ":"
        const val LINK_VALUE_NULL = "#"

        fun from(bytes: UByteArray) = Directory().fromUByteArray(bytes)
        fun from(link: String) = Directory().fromString(link)

        protected fun from(it: Iterator<String>) = Directory().fromStringIterator(it)

        fun randomPath(
            directoryDepthMax: Int = 3,
            nameLengthMax: Int = 3,
            nameLetters: String = "aze",
            separator: Char? = '/'
        ): String {
            val directoryDepth: Int = AppRandomNumber.nextInt(directoryDepthMax) + 1
            val directory = StringBuilder()
            for (i in 0 until directoryDepth) {
                val nameLength: Int = AppRandomNumber.nextInt(nameLengthMax) + 2
                val name = StringBuilder()
                for (j in 0 until nameLength) {
                    name.append(nameLetters[AppRandomNumber.nextInt(nameLetters.length)])
                }
                directory.append(name).append(separator)
            }
            return directory.toString()
        }

        fun toLinkString(storage: StoragePackage.Type, path: StoragePackage.Path) =
            toLinkString(storage.name, path.name)

        fun toLinkString(storage: StoragePackage.Type, path: String?) =
            toLinkString(storage.name, path)

        fun toLinkString(storage: StoragePackage.Type) = toLinkString(storage.name, null)

        fun toLinkString(
            storage: String?,
            path: String?
        ): String {
            val data = StringBuilder()
            data.append(storage ?: LINK_VALUE_NULL).append(LINK_SEPARATOR)
            data.append(if (path != null) path.toStringBase49() else LINK_VALUE_NULL)
            return data.toString()
        }

        fun consumeIterator(it: Iterator<String>) {
            fromStringIterator(null, it)
        }

        private fun fromStringIterator(directory: Directory?, it: Iterator<String>): Directory? {
            if (directory == null) {
                it.next()
                it.next()
            } else {
                val storageString = it.next()
                directory._storage = StoragePackage.Type.valueOf(storageString)
                var pathString = it.next()
                directory._relativePath = null
                directory._relativePathString = null
                if (LINK_VALUE_NULL != pathString) {
                    pathString = pathString.toStringChar()!!
                    directory._relativePath = StoragePackage.Path.valueOf(pathString)
                    if (directory._relativePath == null) {
                        directory._relativePathString = pathString
                    }
                }
            }
            return directory
        }
    }
}