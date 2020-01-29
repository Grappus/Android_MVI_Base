package com.grappus.android.api.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by nimish@grappus.com on 26/01/2020.
 * Description - Base api response class to be extended by a standard api response.
 */

data class ApiResponse<T>(
    @SerializedName("data")
    val payload: T?,
    @SerializedName("resultsCount")
    val resultsCount: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("version")
    val version: Int = 0,
    @SerializedName("status")
    val status: Int = 0
)