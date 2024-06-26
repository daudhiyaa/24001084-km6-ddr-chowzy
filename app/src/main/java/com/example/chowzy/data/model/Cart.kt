package com.example.chowzy.data.model

data class Cart(
    var id: Int? = null,
    var productId: String? = null,
    var productName: String,
    var productImgUrl: String,
    var productPrice: Int,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null
)