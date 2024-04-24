package com.example.chowzy.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.auth.AuthRepository

class SplashViewModel(private val repository: AuthRepository): ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}