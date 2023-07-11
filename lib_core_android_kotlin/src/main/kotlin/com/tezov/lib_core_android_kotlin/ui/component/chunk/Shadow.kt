/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_kotlin.ui.component.chunk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.unit.Dp

object Shadow {

    object Line {

        @Composable
        operator fun invoke(
            modifier: Modifier = Modifier,
            elevation: Dp,
            ambientColor: Color = DefaultShadowColor,
            spotColor: Color = DefaultShadowColor,
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(elevation)
                    .shadow(elevation, ambientColor = ambientColor, spotColor = spotColor)
            )
        }


    }

}