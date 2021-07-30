package com.deep.app.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Prateek Gupta
 * @version 1.0
 */
public final class ParseUtil {
    private ParseUtil() {
    }

    public static <T> T getObject(String json, Class<T> className) throws IOException {
        if (json != null) {
            return new Gson().fromJson(json, className);
        }
        return null;
    }

    public static <T> List<T> getList(String json, Class<T> className) throws IOException {
        if (json != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<T>>() {
            }.getType();
            return (List<T>) gson.fromJson(json, listType);
        }
        return null;
    }

    public static String getJson(Object object) throws IOException {
        if (object != null) {
            return new Gson().toJson(object);
        }
        return null;
    }
}
