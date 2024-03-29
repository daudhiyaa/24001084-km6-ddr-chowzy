package com.example.chowzy.data.datasource.product

import com.example.chowzy.data.model.Menu

interface FoodsDataSource {
    fun getFoods() : List<Menu>
}