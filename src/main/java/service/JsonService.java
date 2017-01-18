package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.istack.internal.Nullable;
import model.Note;

import java.lang.reflect.Type;

public class JsonService {

    private static final Gson gson = new GsonBuilder().setDateFormat(Note.DATE_FORMAT).create();

    public static String toJson(@Nullable Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(@Nullable String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
