package service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.Note

import java.lang.reflect.Type

class JsonService {
    companion object {
        val gson: Gson = GsonBuilder().setDateFormat(Note.DATE_FORMAT).create()

        fun toJson(obj: Any?): String {
            return gson.toJson(obj)
        }

        fun <T> fromJson(json: String, typeOfT: Type): T {
            return gson.fromJson(json, typeOfT)
        }

        fun jsonToMap(json: String): MutableMap<String, Any> {
            val typeOfT: Type = object : TypeToken<MutableMap<String, Any>>() {}.type
            return fromJson(json, typeOfT)
        }
    }
}
