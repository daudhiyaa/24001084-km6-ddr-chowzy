package com.example.chowzy.data.datasource.auth

import com.example.chowzy.data.source.firebase.FirebaseServices
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseServices
    private lateinit var authDataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authDataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email = "daudhiya.31@gmail.com"
            val password = "daudhiya123"
            coEvery { service.doLogin(email, password) } returns true
            val result = authDataSource.doLogin(email, password)
            coVerify { service.doLogin(email, password) }
            assertEquals(true, result)
        }
    }

    @Test
    fun doRegister() {
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { service.updateProfile(any()) } returns true
            val result = authDataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            val newPassword = "123daudhiya"
            coEvery { service.updatePassword(newPassword) } returns true
            val result = authDataSource.updatePassword(newPassword)
            coVerify { service.updatePassword(newPassword) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            val newEmail = "daudhiya123@gmail.com"
            coEvery { service.updateEmail(newEmail) } returns true
            val result = authDataSource.updateEmail(newEmail)
            coVerify { service.updateEmail(newEmail) }
            assertEquals(true, result)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { service.requestChangePasswordByEmail() } returns true
            val result = authDataSource.requestChangePasswordByEmail()
            verify { service.requestChangePasswordByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { service.doLogout() } returns true
            val result = authDataSource.doLogout()
            verify { service.doLogout() }
            assertEquals(true, result)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { service.isLoggedIn() } returns true
            val result = authDataSource.isLoggedIn()
            verify { service.isLoggedIn() }
            assertEquals(true, result)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val mockUser = mockk<FirebaseUser>()
            every { service.getCurrentUser() } returns mockUser
            every { mockUser.uid } answers { "uuid" }
            every { mockUser.displayName } answers { "Daud" }
            every { mockUser.email } answers { "daudhiya.31@gmail.com" }

            val currUser = authDataSource.getCurrentUser()
            verify { service.getCurrentUser() }
            assertEquals("uuid", currUser?.id)
            assertEquals("Daud", currUser?.name)
            assertEquals("daudhiya.31@gmail.com", currUser?.email)
        }
    }
}