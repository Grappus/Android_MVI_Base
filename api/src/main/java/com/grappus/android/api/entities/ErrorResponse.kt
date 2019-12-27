package com.grappus.android.api.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - Sample Error Response
 */

@Keep
data class ErrorResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String
)