package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Note;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonService {

    private static final Gson gson = new GsonBuilder().setDateFormat(Note.DATE_FORMAT).create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static Map<String, Object> jsonToMap(String json) {
        return JsonService.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
    }
}
