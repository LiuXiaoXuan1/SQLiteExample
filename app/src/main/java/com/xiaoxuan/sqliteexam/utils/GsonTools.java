package com.xiaoxuan.sqliteexam.utils;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by xiaoxuan on 2015/7/7.
 */
public class GsonTools {
    public static <T> T getclass(String json, Class<T> clT) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(json, clT);
        } catch (Exception e) {
            Log.e("fromJsonError", e.getMessage());
        }
        return t;
    }

    public static String creatGsonString(Object o) {
        String str = "";
        try {
            Gson gson = new Gson();
            str = gson.toJson(o);
        } catch (Exception e) {
            Log.e("toJsonError", e.getMessage());
        }
        return str;
    }
}
