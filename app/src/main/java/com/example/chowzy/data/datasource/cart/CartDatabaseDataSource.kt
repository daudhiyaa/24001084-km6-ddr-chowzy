package com.example.chowzy.data.datasource.cart

import com.example.chowzy.data.source.local.database.dao.CartDao
import com.example.chowzy.data.source.local.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow

class CartDatabaseDataSource(
    private val dao: CartDao
) : CartDataSource {
    override fun getAllCarts(): Flow<List<CartEntity>> = dao.getAllCarts()

    override suspend fun insertCart(cart: CartEntity): Long = dao.insertCart(cart)
    override suspend fun updateCart(cart: CartEntity): Int = dao.updateCart(cart)
    override suspend fun deleteCart(cart: CartEntity): Int = dao.deleteCart(cart)
    override suspend fun deleteAll() = dao.deleteAll()
}