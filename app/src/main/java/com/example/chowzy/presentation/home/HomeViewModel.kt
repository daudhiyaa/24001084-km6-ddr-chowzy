package com.example.chowzy.presentation.home

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.menu.MenuRepository

class HomeViewModel(
    private val productRepository: MenuRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    fun getMenus() = productRepository.getMenus()
    fun getCategories() = categoryRepository.getCategories()
}