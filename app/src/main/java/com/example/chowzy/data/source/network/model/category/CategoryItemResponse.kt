package com.example.chowzy.data.source.network.model.category

import com.google.gson.annotations.SerializedName

data class CategoryItemResponse(
    @SerializedName("image_url")
    val image_url: String? = null,
    @SerializedName("nama")
    val nama: String? = null,
)
