package com.example.chowzy.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.user.UserRepository

class SplashViewModel(private val repository: UserRepository): ViewModel() {
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}