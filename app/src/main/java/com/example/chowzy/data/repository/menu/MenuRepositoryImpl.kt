package com.example.chowzy.data.repository.menu

import com.example.chowzy.data.datasource.menu.MenuDataSource
import com.example.chowzy.data.mapper.toMenus
import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.source.network.model.checkout.CheckoutItemRequestResponse
import com.example.chowzy.data.source.network.model.checkout.CheckoutRequestResponse
import com.example.chowzy.utils.ResultWrapper
import com.example.chowzy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenuData(categorySlug).data.toMenus()
        }
    }

    override fun createOrder(
        profile: String,
        cart: List<Cart>,
        totalPrice: Int
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestResponse(
                username = profile,
                orders = cart.map {
                    CheckoutItemRequestResponse(
                        nama = it.productName,
                        harga = it.productPrice,
                        qty = it.itemQuantity,
                        catatan = it.itemNotes
                    )
                },
                total = totalPrice
            )
            ).status ?: false
        }
    }
}