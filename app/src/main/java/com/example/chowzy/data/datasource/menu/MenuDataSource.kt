package com.example.chowzy.data.datasource.menu

import com.example.chowzy.data.source.network.model.menu.MenusResponse

interface MenuDataSource {
    suspend fun getMenuData(categorySlug: String? = null): MenusResponse
}