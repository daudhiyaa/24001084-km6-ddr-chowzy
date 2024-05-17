package com.example.chowzy.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.preference.PreferenceRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val authRepository: AuthRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(preferenceRepository.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getListMode(): Int {
        return if (preferenceRepository.isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        preferenceRepository.setUsingGridMode(!currentValue)
    }

    fun getMenu(categoryName: String? = null) =
        menuRepository.getMenus(categoryName).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun getCurrentUser() = authRepository.getCurrentUser()

    fun isLoggedIn() = authRepository.isLoggedIn()
}