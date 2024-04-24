package com.example.chowzy.data.repository.auth

import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.model.User
import com.example.chowzy.utils.ResultWrapper
import com.example.chowzy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val dataSource: AuthDataSource): AuthRepository {
    override fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override fun doRegister(
        name: String,
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doRegister(name, email, password) }
    }

    override fun updateProfile(name: String?): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateProfile(name) }
    }

    override fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updatePassword(newPassword) }
    }

    override fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateEmail(newEmail) }
    }

    override fun requestChangePasswordByEmail(): Boolean {
        return dataSource.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return dataSource.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser()
    }

}