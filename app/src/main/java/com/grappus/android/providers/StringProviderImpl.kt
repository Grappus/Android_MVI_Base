package com.grappus.android.providers

import android.app.Application
import com.grappus.android.R
import com.grappus.android.core.providers.StringId
import com.grappus.android.core.providers.StringProvider
import javax.inject.Inject

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - String provider
 */

class StringProviderImpl @Inject constructor(
    private val application: Application
) : StringProvider {

    override fun getString(stringId: Int): String = application.getString(
        when (stringId) {
            StringId.ERROR_UNKNOWN -> R.string.error_unknown
            StringId.ERROR_NO_INTERNET -> R.string.error_internet
            StringId.ERROR_TIME_OUT -> R.string.error_timeout
            StringId.ERROR_NETWORK_400 -> R.string.error_network_400
            StringId.ERROR_NETWORK_408 -> R.string.error_network_408
            StringId.ERROR_NETWORK_500 -> R.string.error_network_500
            StringId.ERROR_NETWORK_501 -> R.string.error_network_501
            StringId.ERROR_NETWORK_503 -> R.string.error_network_503
            StringId.ERROR_NETWORK_504 -> R.string.error_network_504
            else -> throw Exception("Invalid string id!")
        }
    )
}