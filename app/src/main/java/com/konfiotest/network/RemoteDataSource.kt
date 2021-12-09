package com.konfiotest.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.konfiotest.model.Dog
import com.konfiotest.model.response.ErrorResponse

class RemoteDataSource(private val dogsApiService: DogsApiService) {

    suspend fun getDogList(): NetworkResponse<ArrayList<Dog>, ErrorResponse> {
        return dogsApiService.getDogList()
    }

}