package com.example.chowzy.data.mapper

import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.source.local.database.entity.CartEntity

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    productId = this?.productId.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    productName = this?.productName.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productImgUrl = this?.productImgUrl.orEmpty()
)

fun CartEntity?.toCart() = Cart(
    id = this?.id,
    productId = this?.productId.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    productName = this?.productName.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productImgUrl = this?.productImgUrl.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }