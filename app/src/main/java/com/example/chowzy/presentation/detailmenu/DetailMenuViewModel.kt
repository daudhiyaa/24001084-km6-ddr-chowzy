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
    val food = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRAS_ITEM)

    val foodQtyLiveData = MutableLiveData(0).apply {
        postValue(0)
    }

    val totalPriceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    fun add() {
        val count = (foodQtyLiveData.value ?: 0) + 1
        foodQtyLiveData.postValue(count)
        totalPriceLiveData.postValue(food?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((foodQtyLiveData.value ?: 0) > 0) {
            val count = (foodQtyLiveData.value ?: 0) - 1
            foodQtyLiveData.postValue(count)
            totalPriceLiveData.postValue(food?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart() : LiveData<ResultWrapper<Boolean>>{
        return food?.let {
            val qty = foodQtyLiveData.value ?: 0
            cartRepository.createCart(it, qty).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Product not found"))) }
    }
}