package com.example.chowzy.data.repository.category

import com.example.chowzy.data.datasource.category.CategoryDataSource
import com.example.chowzy.data.model.Category

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()
}