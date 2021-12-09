package com.konfiotest.network

import com.konfiotest.model.Dog

class RepositoryImpl(val remoteDataSource: RemoteDataSource) : Repository {

    override suspend fun getListDogs(): ArrayList<Dog> {
        val response = remoteDataSource.getDogList()
        var parsedResponse = ArrayList<Dog>()
        try {
            parsedResponse = ResponseParser.parseDataDog(response)
        }catch (e : Exception) {
            e.printStackTrace()
        }
        return parsedResponse
    }

}