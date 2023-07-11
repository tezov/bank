/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_java.type.unit;

public enum UnitByte implements defEnumUnit<UnitByte> {
    o(1L) {
        @Override
        public long convert(long value) {
            return value;
        }

        @Override
        public long convert(long value, UnitByte unit) {
            return value * unit.value;
        }
    }, Ko(1024L), Mo(Ko.value * Ko.value), Go(Ko.value * Ko.value * Ko.value);
    public final static String SEPARATOR = ".";
    long value;

    UnitByte(long value) {
        this.value = value;
    }

    public static UnitByte find(int value) {
        UnitByte[] values = values();
        for (UnitByte u : values) {
            if (u.value == value) {
                return u;
            }
        }
        return null;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String getSeparator() {
        return SEPARATOR;
    }

    @Override
    public long convert(long value) {
        return value / this.value;
    }

    @Override
    public long convert(long value, UnitByte unit) {
        return (value * unit.value) / this.value;
    }

    @Override
    public float convert(float value) {
        return value / this.value;
    }

    @Override
    public float convert(float value, UnitByte unit) {
        return (value * unit.value) / this.value;
    }

}
