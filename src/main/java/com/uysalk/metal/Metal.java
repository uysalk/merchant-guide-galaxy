package com.uysalk.metal;

import java.util.function.Supplier;

/**
 * Created by uysal.kara on 12.01.2017.
 */
public class Metal {
    public final String name;
    public final Supplier<Double> value;

    public Metal(String name, Supplier<Double> value) {
        this.name = name;
        this.value = value;
    }
}
