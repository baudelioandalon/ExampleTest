package com.boreal.test.core.utils.core.retrofit

import com.boreal.test.core.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServices {

    @GET
    fun serviceResponseBody(
        @Url url: String,
        @Query("api_key") api_key: String = BuildConfig.MOVIES_API_KEY
    ): Call<Any>

}