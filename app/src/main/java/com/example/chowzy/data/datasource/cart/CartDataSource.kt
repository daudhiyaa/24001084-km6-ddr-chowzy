package com.example.chowzy.data.datasource.cart

import com.example.chowzy.data.source.local.database.dao.CartDao
import com.example.chowzy.data.source.local.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getAllCarts(): Flow<List<CartEntity>>

    suspend fun insertCart(cart: CartEntity): Long
    suspend fun updateCart(cart: CartEntity): Int
    suspend fun deleteCart(cart: CartEntity): Int
    suspend fun deleteAll()
}