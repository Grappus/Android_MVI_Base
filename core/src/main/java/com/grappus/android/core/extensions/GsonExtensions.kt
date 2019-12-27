package com.grappus.android.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - simplify parsing JSON objects with [Gson]
 */

/**
 * Extension method to simplify parsing JSON objects with [Gson].
 *
 * e.g.
 *
 * <code>
 *     val cookieList = gson.fromJson<List<Cookie>>(jsonString)
 * </code>
 */
inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)