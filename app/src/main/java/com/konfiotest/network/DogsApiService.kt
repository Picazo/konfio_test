package com.konfiotest.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.konfiotest.model.Dog
import com.konfiotest.model.response.ErrorResponse
import retrofit2.http.GET

interface DogsApiService {

    @GET("913867691262230528")
    suspend fun getDogList(): NetworkResponse<ArrayList<Dog>, ErrorResponse>
}