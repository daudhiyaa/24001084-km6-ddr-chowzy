package com.example.chowzy.data.repository.category

import com.example.chowzy.data.datasource.category.CategoryDataSource
import com.example.chowzy.data.mapper.toCategories
import com.example.chowzy.data.model.Category
import com.example.chowzy.utils.ResultWrapper
import com.example.chowzy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategoryData().data.toCategories() }
    }
}