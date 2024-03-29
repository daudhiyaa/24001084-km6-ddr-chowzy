package com.example.chowzy.data.repository.cart

import com.example.chowzy.data.datasource.cart.CartDataSource
import com.example.chowzy.data.mapper.toCartEntity
import com.example.chowzy.data.mapper.toCartList
import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.source.local.database.entity.CartEntity
import com.example.chowzy.utils.ResultWrapper
import com.example.chowzy.utils.proceed
import com.example.chowzy.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CartRepositoryImpl(private val cartDataSource: CartDataSource) : CartRepository {
    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>> {
        return cartDataSource.getAllCarts()
            .map {
                // mapping into cartlist and sum into total price
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf {
                        it.productPrice * it.itemQuantity
                    }
                    Pair(result, totalPrice)
                }
            }.map {
                // map to check when list is empty
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun createCart(
        food: Menu,
        quantity: Int,
        notes: String?
    ): Flow<ResultWrapper<Boolean>> {
        return food.id?.let { foodId ->
            // when id is not null
            proceedFlow {
                val affectedRow = cartDataSource.insertCart(
                    CartEntity(
                        productId = foodId,
                        itemQuantity = quantity,
                        productName = food.name,
                        productPrice = food.price,
                        productImgUrl = food.imgUrl,
                        itemNotes = notes
                    )
                )
                affectedRow > 0
            }
        } ?: flow {
            // when id is not exist
            emit(ResultWrapper.Error(IllegalStateException("Product Id not found")))
        }
    }

    override fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity--
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity++
        }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }
}