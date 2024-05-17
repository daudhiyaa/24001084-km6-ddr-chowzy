package com.example.chowzy.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chowzy.MainCoroutineRule
import com.example.chowzy.data.repository.auth.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var authRepo: AuthRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(authRepo))
    }

    @Test
    fun isEditMode() {
    }

    @Test
    fun getProfile() {
    }

    @Test
    fun changeEditMode() {
    }

    @Test
    fun doLogout() {
        every { authRepo.doLogout() } returns true
        val result = viewModel.doLogout()
        assertEquals(true, result)
    }
}