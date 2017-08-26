package service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

class JsonService {
    companion object {
        val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        fun <T> fromJson(json: String, typeOfT: Type): T? {
            try {
                return gson.fromJson(json, typeOfT)
            } catch (e: Exception) {
                return null
            }
        }

        fun jsonToMap(json: String): MutableMap<String, Any>? {
            val typeOfT: Type = object : TypeToken<MutableMap<String, Any>>() {}.type
            return fromJson(json, typeOfT)
        }
    }
}
