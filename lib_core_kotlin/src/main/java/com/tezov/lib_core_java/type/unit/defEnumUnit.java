/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_java.type.unit;

public interface defEnumUnit<E extends Enum<E>> {
    long convert(long value);

    long convert(long value, E unit);

    float convert(float value);

    float convert(float value, E unit);

    String getSeparator();

}
