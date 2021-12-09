package com.konfiotest.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.konfiotest.model.Dog
import com.konfiotest.model.response.ErrorResponse

object ResponseParser {

    fun parseDataDog(response: NetworkResponse<ArrayList<Dog>, ErrorResponse>): ArrayList<Dog>{
        var data = ArrayList<Dog>()
        when(response) {
            is NetworkResponse.Success -> {
               data = response.body
            }
            is NetworkResponse.NetworkError -> {}
            is NetworkResponse.ServerError -> {}
            else -> {}
        }
        return data
    }

}