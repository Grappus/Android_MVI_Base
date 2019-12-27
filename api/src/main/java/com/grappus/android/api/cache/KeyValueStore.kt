package com.grappus.android.api.cache

import android.content.Context
import android.content.SharedPreferences
import com.grappus.android.api.BuildConfig
import javax.inject.Inject

/**
 * Created by nimish@grappus.com on 12/12/2019.
 */

interface KeyValueStore {
    fun saveBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean

    fun saveString(key: String, value: String)
    fun getString(key: String): String?

    fun saveInt(key: String, value: Int)
    fun getInt(key: String): Int?

    fun saveLong(key: String, value: Long)
    fun getLong(key: String): Long

    fun saveFloat(key: String, value: Float)
    fun getFloat(key: String): Float

    fun remove(key: String)
}

class KeyValueStoreImpl @Inject constructor(private val context: Context) : KeyValueStore {

    //TODO why sharedPreferences is not injected instead initialised here
    protected val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(BuildConfig.LIBRARY_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).commit()
    }

    override fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    override fun saveString(key: String, value: String) {
        editor.putString(key, value).commit()
    }

    override fun getString(key: String) = sharedPreferences.getString(key, null)

    override fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).commit()
    }

    override fun getInt(key: String) = sharedPreferences.getInt(key, 0)

    override fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).commit()
    }

    override fun getLong(key: String) = sharedPreferences.getLong(key, 0)

    override fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).commit()
    }

    override fun getFloat(key: String) = sharedPreferences.getFloat(key, 0.0f)

    override fun remove(key: String) {
        editor.remove(key).commit()
    }
}
