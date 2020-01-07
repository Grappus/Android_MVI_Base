package com.grappus.android.logic.cache

import androidx.annotation.VisibleForTesting
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grappus.android.core.extensions.fromJson

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - common TypeConverters
 */

class CacheConverters {
    @TypeConverter
    fun fromListStringToString(value: List<String?>?): String? = toJson(value)

    @TypeConverter
    fun fromStringToListString(value: String): List<String?>? = fromJson(value)
}

@VisibleForTesting
internal var converterGson: Gson = GsonBuilder().create()

private inline fun <reified T> fromJson(value: String?): T? = if (value != null) {
    converterGson.fromJson(value)
} else {
    null
}

private fun <T> toJson(value: T?): String? = if (value != null) {
    converterGson.toJson(value)
} else {
    null
}