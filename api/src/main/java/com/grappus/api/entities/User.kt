package com.grappus.api.entities

import com.google.gson.annotations.SerializedName

//sample class for the user object to be changed as per api response
data class User(
    @SerializedName("personId")
    val userId: Long = 0,
    @SerializedName("fullName")
    val fullName: String = "",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
    @SerializedName("email")
    val email: String = ""
)