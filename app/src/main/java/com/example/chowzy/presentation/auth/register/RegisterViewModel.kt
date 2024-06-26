package com.example.chowzy.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: AuthRepository): ViewModel() {
    fun doRegister(name: String, email: String, password: String): LiveData<ResultWrapper<Boolean>> {
        return repository.doRegister(name, email, password).asLiveData(Dispatchers.IO)
    }
}