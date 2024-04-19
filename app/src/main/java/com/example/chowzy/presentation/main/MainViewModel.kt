package com.example.chowzy.presentation.main

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.user.UserRepository

class MainViewModel (private val repository: UserRepository): ViewModel(){
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}