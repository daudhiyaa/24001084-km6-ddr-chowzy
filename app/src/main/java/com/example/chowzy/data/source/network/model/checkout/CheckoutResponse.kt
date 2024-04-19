package com.example.chowzy.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutResponse(
    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: Boolean? = null
)