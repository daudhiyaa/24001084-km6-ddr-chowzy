package com.example.chowzy.data.repository.menu

import com.example.chowzy.data.model.Menu

interface MenuRepository {
    fun getMenus(): List<Menu>
}
