package com.grappus.api.cache

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson

/**
 * Created by nimish@grappus.com on 12/12/2019.
 *
 * Description - BaseStore provides base class for getting data in different forms
 */

abstract class BaseStore(val keyValueStore: KeyValueStore, val gson: Gson) {

    @VisibleForTesting
    internal inline fun <reified T> getData(key: String): T? {
        return when (T::class) {
            String::class -> keyValueStore.getString(key) as T?
            Int::class -> keyValueStore.getInt(key) as T
            Long::class -> keyValueStore.getLong(key) as T
            Boolean::class -> keyValueStore.getBoolean(key) as T
            Float::class -> keyValueStore.getFloat(key) as T
            else -> {
                val data = keyValueStore.getString(key)
                if (data != null && data.isNotEmpty()) {
                    gson.fromJson(data, T::class.java)
                } else {
                    null
                }
            }
        }
    }

    @VisibleForTesting
    internal fun <T> getSerialisedData(value: T?): String? {
        return value?.let { gson.toJson(it) }
    }

    @VisibleForTesting
    internal inline fun <reified T> getDeserialisedData(value: String?): T? {
        return value?.let { gson.fromJson(it, T::class.java) }
    }

    @VisibleForTesting
    internal fun <T> setData(key: String, data: T?) {
        keyValueStore.apply {
            if (data == null) {
                remove(key)
            } else {
                when (data) {
                    is String -> saveString(key, data)
                    is Long -> saveLong(key, data)
                    is Float -> saveFloat(key, data)
                    is Int -> saveInt(key, data)
                    is Boolean -> saveBoolean(key, data)
                    else -> saveString(key, gson.toJson(data))
                }
            }
        }
    }

    internal object Keys {
        const val AUTH_TOKEN = "auth_token"
        const val AUTH_USER = "auth_user"
    }
}