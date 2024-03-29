package com.example.chowzy.data.datasource.category

import com.example.chowzy.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return mutableListOf(
            Category(image = "https://github.com/daudhiyaa/chowzy-assets/blob/main/category_img/category_rice2.png?raw=true", name = "Rice"),
            Category(image = "https://github.com/daudhiyaa/chowzy-assets/blob/main/category_img/category_noodle.png?raw=true", name = "Noodle"),
            Category(image = "https://github.com/daudhiyaa/chowzy-assets/blob/main/category_img/category_beverage3.png?raw=true", name = "Beverage"),
            Category(image = "https://github.com/daudhiyaa/chowzy-assets/blob/main/category_img/category_dessert.png?raw=true", name = "Dessert"),
            Category(image = "https://github.com/daudhiyaa/chowzy-assets/blob/main/category_img/category_snack2.png?raw=true", name = "Snack")
        )
    }
}