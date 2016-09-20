package com.wly.looklookdemo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Created by Candy on 2016/9/8.
 */
public class JsonUtils {

    /**
     * 解析实体数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class<?> clazz) {

        Object obj = null;
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
        try {
            obj = gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            Log.e("JsonUtil-->>", e.toString());
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException e1) {
            } catch (IllegalAccessException e1) {
            }
            return obj;
        }
        return obj;

    }
}
