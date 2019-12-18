package com.grappus.api

import com.grappus.api.entities.AuthRequest
import com.grappus.api.entities.LoginResponse
import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by nimish@grappus.com on 14/12/2019.
 * Description -
 */

/*Sample class to be replaced later. Used for Demonstration purposes*/
interface AuthApi {

    @POST("auth/login")
    fun login(@Header(HttpConstants.AUTHORIZATION_HEADER) basicAuth: AuthRequest): Single<LoginResponse>
}