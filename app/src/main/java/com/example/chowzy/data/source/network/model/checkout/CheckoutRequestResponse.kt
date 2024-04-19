package com.example.chowzy.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutRequestResponse(
    @SerializedName("total")
    val total: Int? = null,

    @SerializedName("orders")
    val orders: List<CheckoutItemRequestResponse>? = null,

    @SerializedName("username")
    val username: String? = null
)