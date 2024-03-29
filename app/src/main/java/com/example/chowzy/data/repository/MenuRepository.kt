package com.example.chowzy.data.repository

import com.example.chowzy.data.datasource.product.FoodsDataSource
import com.example.chowzy.data.model.Menu

interface ProductRepository {
    fun getFoods(): List<Menu>
}

class ProductRepositoryImpl(private val dataSource: FoodsDataSource) : ProductRepository {
    override fun getFoods(): List<Menu> = dataSource.getFoods()
}