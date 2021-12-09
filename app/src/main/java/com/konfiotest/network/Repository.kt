package com.konfiotest.network

import com.konfiotest.model.Dog

interface Repository {
    suspend fun getListDogs():ArrayList<Dog>
}