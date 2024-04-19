package com.example.chowzy.data.repository.menu

import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.model.Menu
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(categorySlug : String? = null) : Flow<ResultWrapper<List<Menu>>>
    fun createOrder(
        profile: String,
        cart: List<Cart>,
        totalPrice: Int
    ): Flow<ResultWrapper<Boolean>>
}
