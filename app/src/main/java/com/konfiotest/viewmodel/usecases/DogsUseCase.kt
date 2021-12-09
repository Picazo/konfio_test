package com.konfiotest.viewmodel.usecases

import com.konfiotest.model.Dog
import com.konfiotest.network.Repository

class DogsUseCase(val repository: Repository) {

    suspend fun getListDogs(): ArrayList<Dog> {
        return repository.getListDogs()
    }

}