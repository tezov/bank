/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_kotlin.buffer.ByteBuffer
import com.tezov.lib_core_kotlin.file.UtilsFile.extension
import com.tezov.lib_core_kotlin.file.UtilsFile.mimeTypeForFullName
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.GatheringByteChannel
import java.nio.channels.ScatteringByteChannel

class FileW : File {

    constructor(pathname: String) : super(pathname)
    constructor(parent: String, child: String) : this(FileW(parent), child)
    constructor(parent: FileW?, child: String) : super(parent, child)

    override fun getParentFile() = super.getParent()?.let { FileW(it) }

    fun createDirectory() = if (!exists()) {
        mkdirs()
    } else {
        true
    }

    val extension: String? get() = name.extension()

    val mineType: String? get() = name.mimeTypeForFullName()

    @Throws(IOException::class)
    fun write(byteBuffer: ByteBuffer) = write(listOf(byteBuffer))

    @Throws(IOException::class)
    fun write(byteBuffers: List<ByteBuffer>) {
        var output: GatheringByteChannel? = null
        try {
            output = FileOutputStream(this).channel
            byteBuffers.forEach {
                output.write(it.buffer)
            }
            output.close()
        } catch (e: IOException) {
            output?.close()
            throw e
        }
    }

    @Throws(IOException::class)
    fun read(): ByteBuffer? = read(null, true)?.get(0)

    @Throws(IOException::class)
    fun read(byteBuffers: MutableList<ByteBuffer>?, readAll: Boolean): List<ByteBuffer>? {
        var _byteBuffers = byteBuffers
        var input: ScatteringByteChannel? = null
        try {
            input = FileInputStream(this).channel
            if (_byteBuffers == null) {
                if (!readAll) {
                    input.close()
                    return null
                }
                _byteBuffers = ArrayList()
            }
            val bufferLength = _byteBuffers.map { it.remaining }.sum()
            val fileLength = length().toInt()
            if (readAll && fileLength > bufferLength) {
                _byteBuffers.add(ByteBuffer.obtain(fileLength - bufferLength))
            }
            _byteBuffers.forEach {
                input.read(it.buffer)
                it.rewind
            }
            input.close()
        } catch (e: IOException) {
            input?.close()
            throw e
        }
        return _byteBuffers
    }
}