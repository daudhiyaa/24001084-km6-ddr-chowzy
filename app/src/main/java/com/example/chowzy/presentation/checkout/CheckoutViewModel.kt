package com.example.chowzy.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.chowzy.data.repository.cart.CartRepository
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun checkoutCart() = menuRepository.createOrder(
        userRepository.getCurrentUser()?.name ?: "",
        checkoutData.value?.payload?.first.orEmpty(),
        checkoutData.value?.payload?.third ?: 0
    ).asLiveData(Dispatchers.IO)

    fun removeAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll()
        }
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}