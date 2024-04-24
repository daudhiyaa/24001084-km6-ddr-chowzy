package com.example.chowzy.presentation.main

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.auth.AuthRepository

class MainViewModel (private val repository: AuthRepository): ViewModel(){
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}