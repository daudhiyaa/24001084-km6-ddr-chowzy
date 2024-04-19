package com.example.chowzy.data.datasource.menu

import com.example.chowzy.data.source.network.model.checkout.CheckoutRequestResponse
import com.example.chowzy.data.source.network.model.checkout.CheckoutResponse
import com.example.chowzy.data.source.network.model.menu.MenusResponse

interface MenuDataSource {
    suspend fun getMenuData(categorySlug: String? = null): MenusResponse
    suspend fun createOrder(payload: CheckoutRequestResponse): CheckoutResponse
}