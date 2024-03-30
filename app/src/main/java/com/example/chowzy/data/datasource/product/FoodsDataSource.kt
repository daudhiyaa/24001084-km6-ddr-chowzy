package com.example.chowzy.data.datasource.product

import com.example.chowzy.data.model.Menu

interface MenusDataSource {
    fun getMenus() : List<Menu>
}