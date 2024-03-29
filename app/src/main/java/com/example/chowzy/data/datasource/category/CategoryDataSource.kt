package com.example.chowzy.data.datasource.category

import com.example.chowzy.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}