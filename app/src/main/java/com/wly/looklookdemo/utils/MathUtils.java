package com.wly.looklookdemo.utils;

/**
 * Created by Candy on 2016/9/19.
 */
public class MathUtils {

    private MathUtils() { }

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}