/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.layout

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.max

@Composable
fun ShrinkableBox(
    modifier: Modifier = Modifier,
    content: @Composable ShrinkableBoxScope.() -> Unit
) {
    val measurePolicy = remember {
        shrinkableBoxMeasurePolicy()
    }
    Layout(
        content = { ShrinkableBoxInstance.content() },
        measurePolicy = measurePolicy,
        modifier = modifier
    )
}

internal class ShrinkableBoxParentData(
    var shrink: Float? = null,
)

interface ShrinkableBoxScope {
    fun Modifier.shrink(value: Float): Modifier
}

internal fun shrinkableBoxMeasurePolicy(): MeasurePolicy {

    return object : MeasurePolicy {

        val Measurable.shrinkableBoxParentData: ShrinkableBoxParentData? get() = (parentData as? ShrinkableBoxParentData)
        val Measurable.shrink: Float? get() = shrinkableBoxParentData?.shrink

        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            if (measurables.isEmpty()) {
                return layout(
                    constraints.minWidth,
                    constraints.minHeight
                ) {}
            }
            var boxWidth = constraints.minWidth
            var boxHeight = constraints.minHeight
            val placeables = arrayOfNulls<Placeable>(measurables.size)
            measurables.fastForEachIndexed { index, measurable ->
                val height = measurable.minIntrinsicHeight(constraints.maxWidth)
                val measureConstraints = measurable.shrink?.let { ratio ->
                    constraints.copy(
                        maxHeight = (height * ratio).toInt(),
                    )
                } ?: run {
                    constraints
                }
                val placeable = measurable.measure(measureConstraints)
                placeables[index] = placeable
                boxWidth = max(boxWidth, placeable.width)
                boxHeight = max(boxHeight, placeable.height)
            }
            return layout(boxWidth, boxHeight) {
                placeables.forEach { placeable ->
                    placeable as Placeable
                    placeable.place(IntOffset.Zero)
                }
            }
        }

    }
}

internal object ShrinkableBoxInstance : ShrinkableBoxScope {

    override fun Modifier.shrink(value: Float): Modifier {
        require(value >= 0.0f) { "invalid heightFactor $value; must be greater or equal than zero" }
        require(value <= 1.0f) { "invalid heightFactor $value; must be lesser or equal than zero" }
        return this.then(
            ModifierShrinkImpl(
                value = value,
                inspectorInfo = debugInspectorInfo {
                    name = "shrink"
                    this.value = value
                    properties["value"] = value
                }
            )
        )
    }
}

internal class ModifierShrinkImpl(
    val value: Float,
    inspectorInfo: InspectorInfo.() -> Unit
) : ParentDataModifier, InspectorValueInfo(inspectorInfo) {

    override fun Density.modifyParentData(parentData: Any?) =
        ((parentData as? ShrinkableBoxParentData) ?: ShrinkableBoxParentData()).also {
            it.shrink = value
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? ModifierShrinkImpl ?: return false
        return value == otherModifier.value
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String =
        "ModifierShrinkImpl(value=$value)"
}

