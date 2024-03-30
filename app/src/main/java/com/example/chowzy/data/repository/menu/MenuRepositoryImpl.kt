package com.example.chowzy.data.repository.menu

import com.example.chowzy.data.datasource.product.MenusDataSource
import com.example.chowzy.data.model.Menu

class MenuRepositoryImpl(private val dataSource: MenusDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenus()
}