package com.example.chowzy.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutItemRequestResponse(
    @SerializedName("nama")
    val nama: String? = null,

    @SerializedName("harga")
    val harga: Int? = null,

    @SerializedName("qty")
    val qty: Int? = null,

    @SerializedName("catatan")
    val catatan: String? = null
)