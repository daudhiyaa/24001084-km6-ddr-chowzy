package com.example.chowzy.data.repository.category

import com.example.chowzy.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}