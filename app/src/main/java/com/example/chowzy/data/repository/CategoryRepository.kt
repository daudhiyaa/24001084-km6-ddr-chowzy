package com.example.chowzy.data.repository

import com.example.chowzy.data.datasource.category.CategoryDataSource
import com.example.chowzy.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()
}