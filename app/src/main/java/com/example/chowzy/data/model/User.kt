package com.example.chowzy.data.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String,
    val name: String,
    val email: String,
//    val imgProfile: Uri?,
)

fun FirebaseUser?.toUser() = this?.let {
    User(
        id = this.uid,
        name = this.displayName.orEmpty(),
        email = this.email.orEmpty(),
//        imgProfile = this.photoUrl,
    )
}