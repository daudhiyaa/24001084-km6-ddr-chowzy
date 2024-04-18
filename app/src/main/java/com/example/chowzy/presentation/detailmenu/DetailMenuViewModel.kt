package com.example.chowzy.presentation.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.repository.cart.CartRepository
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository : CartRepository,
) : ViewModel() {
    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRAS_ITEM)

    val menuQtyLiveData = MutableLiveData(0).apply {
        postValue(0)
    }

    val totalPriceLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    fun add() {
        val count = (menuQtyLiveData.value ?: 0) + 1
        menuQtyLiveData.postValue(count)
        totalPriceLiveData.postValue(menu?.price?.times(count) ?: 0)
    }

    fun minus() {
        if ((menuQtyLiveData.value ?: 0) > 0) {
            val count = (menuQtyLiveData.value ?: 0) - 1
            menuQtyLiveData.postValue(count)
            totalPriceLiveData.postValue(menu?.price?.times(count) ?: 0)
        }
    }

    fun addToCart() : LiveData<ResultWrapper<Boolean>>{
        return menu?.let {
            val qty = menuQtyLiveData.value ?: 0
            cartRepository.createCart(it, qty).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Product not found"))) }
    }
}