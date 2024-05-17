package com.example.chowzy.data.repository.auth

import app.cash.turbine.test
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.model.User
import com.example.chowzy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {
    @MockK
    lateinit var authDS: AuthDataSource
    private lateinit var authRepo: AuthRepository

    private val globalName = "Daud"
    private val globalEmail = "daud.dhiya31@gmail.com"
    private val globalPassword = "testing123"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authRepo = AuthRepositoryImpl(authDS)
    }

    @Test
    fun doLogin() {
        runTest {
            coEvery { authDS.doLogin(globalEmail, globalPassword) } returns true
            authRepo.doLogin(globalEmail, globalPassword).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDS.doLogin(globalEmail, globalPassword) }
            }
        }
    }

    @Test
    fun doRegister() {
        runTest {
            coEvery { authDS.doRegister(globalName, globalEmail, globalPassword) } returns true
            authRepo.doRegister(globalName, globalEmail, globalPassword).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDS.doRegister(globalName, globalEmail, globalPassword) }
            }
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { authDS.updateProfile(any()) } returns true
            authRepo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDS.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updatePassword() {
        val newPassword = "123testing"
        runTest {
            coEvery { authDS.updatePassword(newPassword) } returns true
            authRepo.updatePassword(newPassword).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDS.updatePassword(newPassword) }
            }
        }
    }

    @Test
    fun updateEmail() {
        val newEmail = "daud123@gmail.com"
        runTest {
            coEvery { authDS.updateEmail(newEmail) } returns true
            authRepo.updateEmail(newEmail).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { authDS.updateEmail(newEmail) }
            }
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { authDS.requestChangePasswordByEmail() } returns true
            val result = authRepo.requestChangePasswordByEmail()
            verify { authDS.requestChangePasswordByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { authDS.doLogout() } returns true
            val result = authRepo.doLogout()
            verify { authDS.doLogout() }
            assertEquals(true, result)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { authDS.isLoggedIn() } returns true
            val result = authRepo.isLoggedIn()
            verify { authDS.isLoggedIn() }
            assertEquals(true, result)
        }
    }

    @Test
    fun getCurrentUser() {
        val user =
            User(
                "1",
                "Daud",
                "daud.dhiya31@gmail.com",
            )
        runTest {
            every { authDS.getCurrentUser() } returns user
            val result = authRepo.getCurrentUser()
            assertEquals(user, result)
            verify { authDS.getCurrentUser() }
        }
    }
}