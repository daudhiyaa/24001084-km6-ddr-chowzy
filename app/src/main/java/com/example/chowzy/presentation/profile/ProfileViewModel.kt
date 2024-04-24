package com.example.chowzy.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.auth.AuthRepository

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _isEditMode = MutableLiveData(false)
    val isEditMode: LiveData<Boolean>
        get() = _isEditMode

    fun getProfile() = repository.getCurrentUser()

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        _isEditMode.postValue(!currentValue)
    }

    fun doLogout(): Boolean {
        return repository.doLogout()
    }
}