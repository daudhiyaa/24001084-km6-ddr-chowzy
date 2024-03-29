package com.example.chowzy.presentation.home

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.menu.ProductRepository

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    fun getFoods() = productRepository.getFoods()
    fun getCategories() = categoryRepository.getCategories()
}