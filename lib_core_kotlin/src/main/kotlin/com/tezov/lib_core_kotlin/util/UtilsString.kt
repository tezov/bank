/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.util

import com.tezov.lib_core_java.toolbox.Clock
import com.tezov.lib_core_kotlin.extension.ExtensionNull.isNotNullAndNotEmpty
import com.tezov.lib_core_kotlin.extension.ExtensionNull.nullify
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringBase49
import com.tezov.lib_core_kotlin.type.primitive.BytesTo.toStringHex
import com.tezov.lib_core_kotlin.type.primitive.FloatTo
import com.tezov.lib_core_kotlin.type.primitive.IntTo
import com.tezov.lib_core_kotlin.util.UtilsBytes.random
import java.util.*
import java.util.regex.Pattern
import kotlin.math.ceil
import kotlin.math.min

@OptIn(ExperimentalUnsignedTypes::class)
object UtilsString {
    const val NUMBER_SEPARATOR = "."
    const val NUMBER_NEGATIVE_SIGN = "-"
    const val NUMBER_OVERFLOW = -1

    fun split(s: String, interval: Int): List<String> {
        val arrayLength = ceil(s.length / interval.toDouble()).toInt()
        val result = ArrayList<String>(arrayLength)
        var j = 0
        val lastIndex = result.size - 1
        for (i in 0 until lastIndex) {
            result[i] = s.substring(j, j + interval)
            j += interval
        }
        result[lastIndex] = s.substring(j)
        return result
    }

    fun parseNumber(s: String?, separator: String = NUMBER_SEPARATOR) = s?.let {
        val pattern = Pattern.compile(
            "^(${Pattern.quote(NUMBER_NEGATIVE_SIGN)})?(\\d+)(${Pattern.quote(separator)})?(\\d+)?([a-zA-Z]+)?$"
        )
        pattern.matcher(s).takeIf { it.matches() }?.let {matcher ->
            Number(
                matcher.group(1),
                matcher.group(2)?:"0",
                matcher.group(4)?:"0",
                matcher.group(5)
            )
        }
    }

    fun String.removeLeadingZero() = this.replaceFirst("^0+(?!$)".toRegex(), "")

    fun String.removeNumberSeparatorIfLast() =
        if (NUMBER_SEPARATOR == this[this.length - 1].toString()) {
            this.substring(0, this.length - 1)
        } else {
            this
        }

    fun randomHex(length: Int) = random(length).toStringHex()!!

    fun randomBase49(length: Int) = random(length).toStringBase49()!!

    fun String.containsAtLeastOne(separators: List<String>): Boolean {
        for (sep in separators) {
            if (this.contains(sep)) {
                return true
            }
        }
        return false
    }

    fun String.splitAndKeepSeparator(separators: List<String>): List<String> {
        val patternBuilder = StringBuilder()
        for (sep in separators) {
            patternBuilder.append("(?<=").append(sep).append(")|")
        }
        val pattern: String = if (patternBuilder.isNotEmpty()) {
            patternBuilder.substring(0, patternBuilder.length - 1)
        } else {
            patternBuilder.toString()
        }
        return this.split(pattern)
    }

    fun String.capitalize(separator: String) = this.capitalize(listOf(separator))

    fun String.capitalize(separators: List<String>) = if (!this.containsAtLeastOne(separators)) {
        this.capitalizeFirst()
    } else if (this.length == 1) {
        this
    } else {
        val out = StringBuilder()
        val textSplit = this.splitAndKeepSeparator(separators)
        for (t in textSplit) {
            out.append(t.capitalizeFirst())
        }
        out.toString()
    }

    //todo marche pas avec
    // Côte D'ivoire
    // Blabla (sdjlsd)
    // Balala (jklsdj Mmslss)
    fun String.capitalizeFirst() = if (this.length == 1) {
        this.uppercase(Locale.getDefault())
    } else {
        this.trim { it <= ' ' }.substring(0, 1).uppercase(Locale.getDefault()) +
                this.substring(1).lowercase(Locale.getDefault())
    }

    fun String.appendDateAndTime() = this + "_" + Clock.DateAndTimeTo.string(
        Clock.DateAndTime.now(),
        Clock.FormatDateAndTime.FULL_FILE_NAME
    )

    fun StringBuilder.appendDateAndTime(builder: StringBuilder) = builder.append("_").append(
        Clock.DateAndTimeTo.string(
            Clock.DateAndTime.now(),
            Clock.FormatDateAndTime.FULL_FILE_NAME
        )
    )

    fun String.insert(c: Char, every: Int) = this.replace(".{" + every + "}(?!$)".toRegex(), "$0$c")

    class Number(prefix: String?, integer: String, decimal: String, unit: String?) {
        var integerPrecision = 0
            private set
        var decimalPrecision = 0
            private set

        val isNegative: Boolean
        val unit: String?
        var integer = 0
            private set
        var decimal = 0
            private set

        init {
            isNegative = prefix.isNotNullAndNotEmpty()
            if (integer.isNotNullAndNotEmpty()) {
                if (!isNegative && integer.length <= IntTo.MAX_DIGIT_POSITIVE || isNegative && integer.length <= IntTo.MAX_DIGIT_NEGATIVE) {
                    try {
                        this.integer = integer.toInt()
                    } catch (e: Throwable) {
                        this.integer = NUMBER_OVERFLOW
                    }
                } else {
                    this.integer = NUMBER_OVERFLOW
                }
                integerPrecision = integer.length
            } else {
                this.integer = 0
                integerPrecision = 0
            }
            if (decimal.isNotNullAndNotEmpty()) {
                if (decimal.length <= FloatTo.MAX_DIGIT_DECIMAL) {
                    try {
                        this.decimal = decimal.toInt()
                    } catch (e: Throwable) {
                        this.decimal = NUMBER_OVERFLOW
                    }
                } else {
                    this.decimal = NUMBER_OVERFLOW
                }
                decimalPrecision = decimal.length
            } else {
                this.decimal = 0
                decimalPrecision = 0
            }
            this.unit = unit.nullify()
        }

        val isInteger get() = decimalPrecision == 0
        val isFloat get() = decimalPrecision != 0
        val isOverflow get() = isIntegerOverflow || isDecimalOverflow
        val isDecimalOverflow get() = decimal == NUMBER_OVERFLOW
        val isIntegerOverflow get() = integer == NUMBER_OVERFLOW

        fun toString(withUnit: Boolean): String {
            val data = StringBuilder()
            if (isNegative) {
                data.append(NUMBER_NEGATIVE_SIGN)
            }
            if (integer != NUMBER_OVERFLOW) {
                data.append(integer)
            } else {
                data.append(Int.MAX_VALUE)
            }
            if (decimalPrecision > 0) {
                data.append(NUMBER_SEPARATOR)
                if (decimal != NUMBER_OVERFLOW) {
                    data.append(String.format(Locale.US, "%0" + decimalPrecision + "d", decimal))
                } else {
                    val end: Int = min(IntTo.MAX_DIGIT_POSITIVE, FloatTo.MAX_DIGIT_DECIMAL)
                    var i = 0
                    while (i < end) {
                        data.append("9")
                        i++
                    }
                }
            }
            if (withUnit && unit != null) {
                data.append(unit)
            }
            return data.toString()
        }

    }
}