package com.example.chowzy.data.datasource.category

import com.example.chowzy.data.source.network.model.category.CategoriesResponse
import com.example.chowzy.data.source.network.services.RestaurantApiService

class CategoryApiDataSource(private val service: RestaurantApiService) : CategoryDataSource {
    override suspend fun getCategoryData(): CategoriesResponse {
        return service.getCategories()
    }
}