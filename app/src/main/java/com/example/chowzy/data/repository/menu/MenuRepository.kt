package com.example.chowzy.data.repository.menu

import com.example.chowzy.data.model.Menu

interface ProductRepository {
    fun getFoods(): List<Menu>
}
