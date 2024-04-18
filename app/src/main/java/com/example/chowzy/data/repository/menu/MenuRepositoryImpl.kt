package com.example.chowzy.data.repository.menu

import com.example.chowzy.data.datasource.menu.MenuDataSource
import com.example.chowzy.data.mapper.toMenus
import com.example.chowzy.data.model.Menu
import com.example.chowzy.utils.ResultWrapper
import com.example.chowzy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenuData(categorySlug).data.toMenus()
        }
    }
}