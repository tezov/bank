/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:54
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.paintArt

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import kotlin.math.cos
import kotlin.math.sin

object Shape {

    @Composable
    fun Star(size: Dp, color: Color, formFactor:Float = 0.382f) {
        Canvas(modifier = Modifier.size(size)) {
            val outerRadius = this.size.width / 2.0f
            val innerRadius = outerRadius * formFactor
            val path = Path().apply {
                (0 until 10).forEach { index ->
                    val radius = if (index % 2 == 0) outerRadius else innerRadius
                    val angle =
                        (index * 36f) - 18f // Adjust the angle to make two feet on the same horizontal line
                    val point = center + Offset(
                        x = cos(Math.toRadians(angle.toDouble())).toFloat() * radius,
                        y = sin(Math.toRadians(angle.toDouble())).toFloat() * radius
                    )
                    if (index == 0) {
                        moveTo(point.x, point.y)
                    } else {
                        lineTo(point.x, point.y)
                    }
                }
                close()
            }
            drawPath(path = path, color = color, style = Fill)
        }
    }

}

