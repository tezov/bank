/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.socket

import com.tezov.lib_core_java.type.unit.UnitByte
import com.tezov.lib_core_kotlin.util.UtilsBytes
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.min

object UtilsSocket {
    private val DEFAULT_BUFFER_SIZE = UnitByte.o.convert(20, UnitByte.Mo).toInt()

    //public static int randomPortDynamic(){
    //TODO return AppRandomNumber.nextInt(ValidatorPortDynamic.MIN, ValidatorPortDynamic.MAX);
    //}
    //public static int randomPortUser(){
    //    return AppRandomNumber.nextInt(ValidatorPortUser.MIN, ValidatorPortUser.MAX);
    //}
    //public static InetAddress randomMulticastAddress(){
    //    try{
    //        return InetAddress.getByName(AppRandomNumber.nextInt(224, 239) + "." + AppRandomNumber.nextInt(0, 255) + "." + AppRandomNumber.nextInt(0, 255) + "." + AppRandomNumber.nextInt(0, 255));
    //    } catch(Throwable e){
    //
    //        return null;
    //    }
    //}

    @Throws(IOException::class)
    fun receive(
        source: InputStream,
        destination: OutputStream,
        available: Int,
        bufferSize: Int = DEFAULT_BUFFER_SIZE
    ) {
        var _available = available
        var length: Int
        val buffer: ByteArray = UtilsBytes.obtain(bufferSize).asByteArray()
        while (source.read(buffer, 0, min(_available, bufferSize)).also { length = it } > 0) {
            destination.write(buffer, 0, length)
            _available -= length
        }
        destination.flush()
        if (_available != 0) {
            throw IOException("Did not receive all bytes")
        }
    }
}