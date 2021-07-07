package com.sampletest.data.remote.api

import com.sampletest.data.local.entities.Message
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("eae38bc902dbd")
    fun getResponse(): Call<String>

}