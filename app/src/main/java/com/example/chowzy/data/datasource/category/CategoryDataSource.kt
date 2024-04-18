package com.example.chowzy.data.datasource.category

import com.example.chowzy.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategoryData(): CategoriesResponse
}