package com.example.chowzy.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chowzy.data.model.Profile

class ProfileViewModel : ViewModel() {
    val profileData = MutableLiveData(
        Profile(
            name = "Daud Dhiya'",
            username = "daudhiyaa",
            email = "daud.dhiya31@gmail.com",
            profileImg = "https://avatars.githubusercontent.com/u/90663569?v=4"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}