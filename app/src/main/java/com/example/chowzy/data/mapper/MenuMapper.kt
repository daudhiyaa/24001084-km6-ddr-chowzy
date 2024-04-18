package com.example.chowzy.data.mapper

import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toMenu() = Menu(
    imgUrl = this?.image_url.orEmpty(),
    name = this?.nama.orEmpty(),
    desc = this?.detail.orEmpty(),
    location = this?.alamat_resto.orEmpty(),
    locationURL = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
    price = this?.harga ?: 0,
)

fun Collection<MenuItemResponse>?.toMenus() = this?.map { it.toMenu() } ?: listOf()