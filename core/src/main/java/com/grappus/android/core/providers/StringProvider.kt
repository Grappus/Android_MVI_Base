package com.grappus.android.core.providers

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

interface StringProvider {
    /**
     * Retrieve string with the specified [StringId] from app resources.
     *
     * @throws Exception if an invalid [stringId] was provided.
     */
    fun getString(stringId: Int): String
}

object StringId {
    const val ERROR_UNKNOWN = 1
    const val ERROR_NO_INTERNET = 2
    const val ERROR_TIME_OUT = 3
    const val ERROR_NETWORK_400 = 4
    const val ERROR_NETWORK_408 = 5
    const val ERROR_NETWORK_500 = 6
    const val ERROR_NETWORK_501 = 7
    const val ERROR_NETWORK_503 = 8
    const val ERROR_NETWORK_504 = 9
}