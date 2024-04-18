package com.example.chowzy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String? = UUID.randomUUID().toString(),
    var imgUrl: String,
    var name: String,
    var price: Int,
    var desc: String,
    var location: String,
    var locationURL: String,
) : Parcelable
