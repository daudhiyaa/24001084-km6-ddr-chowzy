package com.example.chowzy.data.repository.category

import com.example.chowzy.data.model.Category
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories() : Flow<ResultWrapper<List<Category>>>
}