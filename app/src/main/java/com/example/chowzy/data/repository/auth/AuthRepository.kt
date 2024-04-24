package com.example.chowzy.data.repository.auth

import com.example.chowzy.data.model.User
import com.example.chowzy.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    @Throws(exceptionClasses = [Exception::class])
    fun doLogin(
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>>

    @Throws(exceptionClasses = [Exception::class])
    fun doRegister(
        name: String,
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>>

    fun updateProfile(name: String? = null): Flow<ResultWrapper<Boolean>>
    fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>
    fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>>
    fun requestChangePasswordByEmail(): Boolean
    fun doLogout(): Boolean
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
}