package com.example.chowzy.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: AuthRepository): ViewModel() {
    fun doLogin(email: String, password: String): LiveData<ResultWrapper<Boolean>> {
        return repository.doLogin(email,password).asLiveData(Dispatchers.IO)
    }
}