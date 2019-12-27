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
            else -> throw Exception("Invalid string id!")
        }
    )
}