package com.konfiotest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konfiotest.model.Dog
import com.konfiotest.viewmodel.usecases.DogsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DogsViewModel(private val dogsUseCase: DogsUseCase) : ViewModel(){

    var dataListDogMd: MutableLiveData<ArrayList<Dog>> = MutableLiveData()
    var errorMessageMd: MutableLiveData<String> = MutableLiveData()

    fun getListDogs(){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                dogsUseCase.getListDogs()
            }
            validateDogListResponse(response)
        }
    }

    private fun validateDogListResponse(dogsList: ArrayList<Dog>) {
        if (!dogsList.isNullOrEmpty()) {
            dataListDogMd.value = dogsList
        } else {
            errorMessageMd.value = "Algo salio mal, intenta más tarde."
        }
    }
}