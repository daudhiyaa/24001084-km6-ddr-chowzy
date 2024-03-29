package com.example.chowzy.data.repository.cart

import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.model.PriceItem
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>>
    fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>,List<PriceItem>, Double>>>

    fun createCart(
        product: Menu,
        quantity: Int,
        notes: String? = null
    ): Flow<ResultWrapper<Boolean>>

    fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>

    suspend fun checkout(items: List<Cart>): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAll()
}