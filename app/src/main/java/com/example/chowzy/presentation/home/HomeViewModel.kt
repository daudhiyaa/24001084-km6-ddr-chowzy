package com.example.chowzy.presentation.home

import androidx.lifecycle.ViewModel
import com.example.chowzy.data.repository.CategoryRepository
import com.example.chowzy.data.repository.ProductRepository

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    fun getFoods() = productRepository.getFoods()
    fun getCategories() = categoryRepository.getCategories()
}