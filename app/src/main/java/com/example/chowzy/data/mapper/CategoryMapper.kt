package com.example.chowzy.data.mapper

import com.example.chowzy.data.model.Category
import com.example.chowzy.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() = Category(
    image = this?.image_url.orEmpty(),
    name = this?.nama.orEmpty()
)

fun Collection<CategoryItemResponse>?.toCategories() = this?.map { it.toCategory() } ?: listOf()