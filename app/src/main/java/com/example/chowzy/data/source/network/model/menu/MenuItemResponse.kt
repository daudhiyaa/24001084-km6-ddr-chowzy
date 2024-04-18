package com.example.chowzy.data.source.network.model.menu

import com.google.gson.annotations.SerializedName

data class MenuItemResponse(
    @SerializedName("image_url")
    val image_url: String? = null,
    @SerializedName("nama")
    val nama: String? = null,
    @SerializedName("harga_format")
    val harga_format: String? = null,
    @SerializedName("harga")
    val harga: Int? = null,
    @SerializedName("detail")
    val detail: String? = null,
    @SerializedName("alamat_resto")
    val alamat_resto : String? = null
)
