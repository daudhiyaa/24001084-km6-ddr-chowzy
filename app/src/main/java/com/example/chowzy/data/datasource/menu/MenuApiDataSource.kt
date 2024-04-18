package com.example.chowzy.data.datasource.menu

import com.example.chowzy.data.source.network.model.menu.MenusResponse
import com.example.chowzy.data.source.network.services.RestaurantApiService

class MenuApiDataSource(private val service : RestaurantApiService): MenuDataSource {
    override suspend fun getMenuData(categorySlug: String?): MenusResponse {
        return service.getMenus(categorySlug)
    }
}