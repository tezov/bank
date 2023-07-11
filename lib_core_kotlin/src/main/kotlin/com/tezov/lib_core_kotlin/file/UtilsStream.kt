/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_kotlin.file.UtilsFile.DigesterCRC32
import com.tezov.lib_core_kotlin.socket.UtilsSocket.receive
import java.io.*

object UtilsStream {

    const val NULL_LENGTH = -1

    fun closeSilently(input: InputStream?) {
        input?.let {
            try {
                it.close()
            } catch (_: Throwable) {
            }
        }
    }

    fun closeSilently(output: OutputStream?) {
        output?.let {
            try {
                it.close()
            } catch (_: Throwable) {
            }
        }
    }

    fun closeSilently(stream: StreamLinker) {
        try {
            stream.close()
        } catch (_: Throwable) {
        }
    }

    interface ProgressListener {
        fun onProgress(current: Int, max: Int)
    }

    open class OutputStreamNotClosable(protected var output: OutputStream) : OutputStream() {
        @Throws(IOException::class)
        override fun write(b: ByteArray) {
            output.write(b)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray, off: Int, len: Int) {
            output.write(b, off, len)
        }

        @Throws(IOException::class)
        override fun flush() {
            output.flush()
        }

        @Throws(IOException::class)
        override fun write(i: Int) {
            output.write(i)
        }

        @Throws(IOException::class)
        override fun close() {
        }

    }

    open class InputStreamNotClosable(protected var input: InputStream) : InputStream() {
        @Throws(IOException::class)
        override fun read(b: ByteArray): Int {
            return input.read(b)
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray, off: Int, len: Int): Int {
            return input.read(b, off, len)
        }

        @Throws(IOException::class)
        override fun skip(n: Long): Long {
            return input.skip(n)
        }

        @Throws(IOException::class)
        override fun available(): Int {
            return input.available()
        }

        @Synchronized
        override fun mark(readlimit: Int) {
            input.mark(readlimit)
        }

        @Synchronized
        @Throws(IOException::class)
        override fun reset() {
            input.reset()
        }

        override fun markSupported(): Boolean {
            return input.markSupported()
        }

        @Throws(IOException::class)
        override fun read(): Int {
            return input.read()
        }

        @Throws(IOException::class)
        override fun close() {
        }
    }

    class InputStreamLimitAvailability(input: InputStream, private var availability: Int) :
        InputStreamNotClosable(input) {
        private val oneByteBuffer: ByteArray = ByteArray(1)

        @Throws(IOException::class)
        override fun read(): Int {
            return if (read(oneByteBuffer, 0, 1) != NULL_LENGTH) {
                oneByteBuffer[0].toInt()
            } else {
                NULL_LENGTH
            }
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray): Int {
            return read(b, 0, b.size)
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray, off: Int, len: Int): Int {
            var _len = len
            if (availability <= 0) {
                return NULL_LENGTH
            }
            val diff = availability - _len
            if (diff < 0) {
                _len = availability
            }
            _len = input.read(b, off, _len)
            if (_len != NULL_LENGTH) {
                availability -= _len
            }
            return _len
        }

        @Throws(IOException::class)
        override fun skip(n: Long): Long {
            var _n = n
            if (availability <= 0) {
                return 0
            }
            val diff = (availability - _n)
            if (diff < 0) {
                _n = availability.toLong()
            }
            val l = input.skip(_n)
            availability -= l.toInt()
            return l
        }

        @Throws(IOException::class)
        override fun available(): Int {
            return availability
        }

        @Synchronized
        override fun mark(readlimit: Int) {
        }

        @Synchronized
        @Throws(IOException::class)
        override fun reset() {
            throw IOException("mark/reset not supported")
        }

        override fun markSupported(): Boolean {
            return false
        }
    }

    abstract class InputStreamW(protected var input: InputStream) : InputStream() {
        var streamLinker: StreamLinker? = null

        constructor(file: File) : this(file.inputStream)

        @Throws(IOException::class)
        override fun read(): Int {
            return input.read()
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray): Int {
            return input.read(b)
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray, off: Int, len: Int): Int {
            return input.read(b, off, len)
        }

        @Throws(IOException::class)
        override fun skip(n: Long): Long {
            return input.skip(n)
        }

        @Throws(IOException::class)
        override fun available(): Int {
            return input.available()
        }

        @Throws(IOException::class)
        override fun close() {
            input.close()
        }

        @Synchronized
        override fun mark(readlimit: Int) {
            input.mark(readlimit)
        }

        @Synchronized
        @Throws(IOException::class)
        override fun reset() {
            input.reset()
        }

        override fun markSupported(): Boolean {
            return input.markSupported()
        }
    }

    open class InputStreamAppendCrc(input: InputStream) : InputStreamW(input) {
        private var digester = DigesterCRC32()
        private var crcBuffer: ByteArrayInputStream? = null
        private val oneByteBuffer = ByteArray(1)

        constructor(file: File) : this(file.inputStream)

        @Throws(IOException::class)
        override fun available(): Int {
            var available = super.available()
            available += crcBuffer?.available() ?: let { DigesterCRC32.length() }
            return available
        }

        @Throws(IOException::class)
        override fun read(): Int {
            return if (read(oneByteBuffer, 0, 1) != NULL_LENGTH) {
                oneByteBuffer[0].toInt()
            } else {
                NULL_LENGTH
            }
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray): Int {
            return read(b, 0, b.size)
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray, off: Int, len: Int): Int {
            crcBuffer?.let {
                return it.read(b, off, len)
            } ?: let {
                val lengthRead = super.read(b, off, len)
                if (lengthRead > 0) {
                    digester.update(b, off, lengthRead)
                    return lengthRead
                } else return ByteArrayInputStream(digester.valueByte.asByteArray()).run {
                    crcBuffer = this
                    it.read(b, off, len)
                }
            }
        }

        @Throws(IOException::class)
        override fun close() {
            crcBuffer?.close()
            digester.reset()
            super.close()
        }


    }

    abstract class InputStreamProgress : InputStreamW, ProgressListener {
        var lengthRead = 0
            protected set
        var lengthToRead: Int? = null
            set(value) {
                lengthRead = 0
                field = value
            }

        constructor(file: File) : super(file)
        constructor(input: InputStream) : super(input)

        @Throws(IOException::class)
        override fun read(): Int {
            val value = super.read()
            if (value > NULL_LENGTH) {
                updateLengthRead(value)
            }
            return value
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray): Int {
            val lengthRead = super.read(b)
            updateLengthRead(lengthRead)
            return lengthRead
        }

        @Throws(IOException::class)
        override fun read(b: ByteArray, off: Int, len: Int): Int {
            val lengthRead = super.read(b, off, len)
            updateLengthRead(lengthRead)
            return lengthRead
        }

        protected fun updateLengthRead(len: Int) {
            if (len > NULL_LENGTH) {
                lengthRead += len
                if (lengthToRead != null) {
//TODO
//                Handler.SECONDARY().post(this, new RunnableW(){
//                    @Override
//                    public void runSafe(){
//                        onProgress(getLengthRead(), lengthToRead);
//                    }
//                });
                }
            }
        }
    }

    abstract class InputStreamProgressAppendCrc(input: InputStream) : InputStreamAppendCrc(input),
        ProgressListener {
        var lengthRead = 0
            protected set
        var lengthToRead: Int? = null
            set(value) {
                lengthRead = 0
                field = value
            }

        constructor(file: File) : this(file.inputStream)

        @Throws(IOException::class)
        override fun read(b: ByteArray, off: Int, len: Int): Int {
            val length = super.read(b, off, len)
            updateLengthRead(length)
            return length
        }

        protected fun updateLengthRead(len: Int) {
            if (len > NULL_LENGTH) {
                lengthRead += len
                if (lengthToRead != null) {
//TODO
//                Handler.SECONDARY().post(this, new RunnableW(){
//                    @Override
//                    public void runSafe(){
//                        onProgress(getLengthRead(), lengthToRead);
//                    }
//                });
                }
            }
        }
    }

    abstract class OutputStreamW(private var output: OutputStream) : OutputStream() {
        var streamLinker: StreamLinker? = null

        constructor(file: File) : this(file.outputStream)

        @Throws(IOException::class)
        override fun write(b: Int) {
            output.write(b)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray) {
            output.write(b)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray, off: Int, len: Int) {
            output.write(b, off, len)
        }

        @Throws(IOException::class)
        override fun flush() {
            output.flush()
        }

        @Throws(IOException::class)
        override fun close() {
            output.close()
        }
    }

    abstract class OutputStreamProgress : OutputStreamW, ProgressListener {
        var lengthWritten = 0
            protected set
        var lengthToWrite: Int? = null
            set(value) {
                lengthWritten = 0
                field = value
            }

        constructor(file: File) : super(file)
        constructor(out: OutputStream) : super(out)

        @Throws(IOException::class)
        override fun write(b: Int) {
            super.write(b)
            updateLengthWritten(1)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray) {
            super.write(b)
            updateLengthWritten(b.size)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray, off: Int, len: Int) {
            super.write(b, off, len)
            updateLengthWritten(len)
        }

        protected fun updateLengthWritten(len: Int) {
            lengthWritten += len
            if (lengthToWrite != null) {
                //TODO
//            Handler.SECONDARY().post(this, new RunnableW(){
//                @Override
//                public void runSafe(){
//                    onProgress(getLengthWritten(), lengthToWrite);
//                }
//            });
            }
        }
    }

    open class OutputStreamCheckCrc(out: OutputStream) : OutputStreamW(out) {
        var lengthWritten = 0
            protected set
        var lengthToWrite: Int? = null
            set(value) {
                lengthWritten = 0
                field = value
            }

        private var digester = DigesterCRC32()
        private var crcBuffer: ByteArrayOutputStream? = null
        private val oneByteBuffer = ByteArray(1)
        private var crcHasBeenChecked = false

        constructor(file: File) : this(file.outputStream)

        @Throws(IOException::class)
        override fun write(b: Int) {
            oneByteBuffer[0] = b.toByte()
            write(oneByteBuffer)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray) {
            write(b, 0, b.size)
        }

        @Throws(IOException::class)
        override fun write(b: ByteArray, off: Int, len: Int) {
            var _off = off
            var _len = len
            if (lengthToWrite == null) {
                throw IOException("totalLengthToWrite is null, use read(InputStream in, Integer len) or setTotalLengthToWrite(Integer value)")
            }
            if (crcBuffer == null) {
                val diff = lengthToWrite!! - DigesterCRC32.length() - (_len + lengthWritten)
                if (diff > 0) {
                    digester.update(b, _off, _len)
                    super.write(b, _off, _len)
                    updateLengthWritten(_len)
                    return
                }
                val remaining = _len + diff
                if (remaining < 0) {
                    throw IOException("remaining is negative")
                }
                if (remaining > 0) {
                    digester.update(b, _off, remaining)
                    super.write(b, _off, remaining)
                    updateLengthWritten(remaining)
                }
                _off += remaining
                _len -= remaining
                crcBuffer = ByteArrayOutputStream(DigesterCRC32.length())
            }
            if (_len - _off + crcBuffer!!.size() > DigesterCRC32.length()) {
                throw IOException("try to write over the crc retrofit.data")
            }
            crcBuffer!!.write(b, _off, _len)
            updateLengthWritten(_len)
            if (crcBuffer!!.size() == DigesterCRC32.length()) {
                if (!digester.equals(crcBuffer!!.toByteArray())) {
                    throw IOException("crc mismatches")
                }
                crcHasBeenChecked = true
            }
        }

        protected open fun updateLengthWritten(len: Int) {
            lengthWritten += len
        }

        @Throws(IOException::class)
        override fun close() {
            crcBuffer?.close()
            digester.reset()
            if (!crcHasBeenChecked) {
                throw IOException("crc has not been checked")
            }
            super.close()
        }


    }

    abstract class OutputStreamProgressCheckCrc(out: OutputStream) : OutputStreamCheckCrc(out),
        ProgressListener {
        constructor(file: File) : this(file.outputStream)

        override fun updateLengthWritten(len: Int) {
            super.updateLengthWritten(len)
            if (lengthToWrite != null) {
//TODO
//            Handler.SECONDARY().post(this, new RunnableW(){
//                @Override
//                public void runSafe(){
//                    onProgress(getLengthWritten(), lengthToWrite);
//                }
//            });
            }
        }
    }

    abstract class StreamLinker {
        var input: InputStream? = null
        var output: OutputStream? = null

        fun getInputNotClosable() = InputStreamNotClosable(input!!) //TODO

        fun getInputNotClosableLimitAvailability(length: Int) =
            InputStreamLimitAvailability(input!!, length)


        @Throws(IOException::class)
        fun available(): Int {
            return input!!.available()
        }

        @Throws(IOException::class)
        fun read(): Int {
            return input!!.read()
        }

        @Throws(IOException::class)
        fun read(b: ByteArray?): Int {
            return input!!.read(b)
        }

        @Throws(IOException::class)
        fun read(b: ByteArray?, off: Int, len: Int): Int {
            return input!!.read(b, off, len)
        }

        val getOutputNotClosable = OutputStreamNotClosable(output!!)

        @Throws(IOException::class)
        fun write(b: Int) {
            output!!.write(b)
        }

        @Throws(IOException::class)
        fun write(b: ByteArray?) {
            output!!.write(b)
        }

        @Throws(IOException::class)
        fun write(b: ByteArray?, off: Int, len: Int) {
            output!!.write(b, off, len)
        }

        @Throws(IOException::class)
        open fun transfer(len: Int?) {

            (input as? InputStreamProgressAppendCrc)?.let {
                it.lengthToRead = len
            } ?: (input as? InputStreamProgress)?.let {
                it.lengthToRead = len
            }

            (output as? OutputStreamProgress)?.let {
                it.lengthToWrite = len
            } ?: (output as? OutputStreamCheckCrc)?.let {
                it.lengthToWrite = len
            }

        }

        @Throws(IOException::class)
        open fun transfer() {
            transfer(null as Int?)
        }

        @Throws(IOException::class)
        open fun transfer(output: OutputStream) {
            transfer(output, null)
        }

        @Throws(IOException::class)
        fun transfer(output: OutputStream, len: Int?) {
            val previousOutput = this.output
            this.output = output
            transfer(len)
            this.output = previousOutput
        }

        @Throws(IOException::class)
        open fun transfer(input: InputStream) {
            transfer(input, null)
        }

        @Throws(IOException::class)
        fun transfer(input: InputStream, len: Int?) {
            val previousInput = this.input
            this.input = input
            transfer(len)
            this.input = previousInput
        }

        @Throws(IOException::class)
        fun close() {
            var eInput: IOException? = null
            var eOutput: IOException? = null
            try {
                closeInput()
            } catch (e: IOException) {
                eInput = e
            }
            try {
                closeOutput()
            } catch (e: IOException) {
                eOutput = e
            }
            eInput?.let { throw it }
            eOutput?.let { throw it }
        }

        @Throws(IOException::class)
        fun closeInput() {
            input!!.close()
        }

        @Throws(IOException::class)
        fun closeOutput() {
            output!!.close()
        }
    }

    class StreamLinkerFile : StreamLinker() {
        @Throws(IOException::class)
        override fun transfer(len: Int?) {
            super.transfer(len)
            transfer(input!!, output!!, len)
        }

        companion object {
            @Throws(IOException::class)
            fun transfer(input: InputStream, out: OutputStream, len: Int?) {
                try {
                    UtilsFile.copy(input, out, len)
                } catch (e: IOException) {
                    throw e
                } catch (e: Throwable) {
                    throw IOException(e.message)
                }
            }
        }
    }

    abstract class StreamLinkerProgress : StreamLinker() {

        @Throws(IOException::class)
        override fun transfer() {
            transfer(available())
        }

        @Throws(IOException::class)
        override fun transfer(output: OutputStream) {
            transfer(output, available())
        }

        @Throws(IOException::class)
        override fun transfer(input: InputStream) {
            transfer(input, available())
        }
    }

    class StreamLinkerFileProgress : StreamLinkerProgress() {
        @Throws(IOException::class)
        override fun transfer(len: Int?) {
            super.transfer(len)
            StreamLinkerFile.transfer(input!!, output!!, len)
        }
    }

    class StreamLinkerSocket : StreamLinker() {

        @Throws(IOException::class)
        override fun transfer(len: Int?) {
            super.transfer(len)
            transfer(input!!, output!!, len)
        }

        companion object {
            @Throws(IOException::class)
            fun transfer(input: InputStream, output: OutputStream, len: Int?) {
                try {
                    receive(input, output, len!!)
                } catch (e: IOException) {
                    throw e
                } catch (e: Throwable) {
                    throw IOException(e.message)
                }
            }
        }
    }

    class StreamLinkerSocketProgress : StreamLinkerProgress() {
        @Throws(IOException::class)
        override fun transfer(len: Int?) {
            super.transfer(len)
            StreamLinkerSocket.transfer(input!!, output!!, len)
        }
    }
}