package com.grappus.api.extensions

import retrofit2.Retrofit

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description -
 */

inline fun <reified T> Retrofit.create(): T = create(T::class.java)