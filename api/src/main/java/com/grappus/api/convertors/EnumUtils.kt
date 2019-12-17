package com.grappus.api.convertors

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

/**
 * Created by nimish@grappus.com on 14/12/2019.
 * Description - uses Reflection to get the serialized name of Enums from their Annotated value
 */

object EnumUtils {

    @Nullable
    fun <E : Enum<E>> getSerialisedNameValue(e: E): String? {
        var value: String? = null
        try {
            value = e.javaClass.getField(e.name).getAnnotation(SerializedName::class.java).value
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        return value
    }
}