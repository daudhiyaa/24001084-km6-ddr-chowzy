package com.example.chowzy.presentation.main

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

class MainViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var authRepo: AuthRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(MainViewModel(authRepo))
    }

    @Test
    fun isLoggedIn() {
        every { authRepo.isLoggedIn() } returns true
        val result = viewModel.isLoggedIn()
        assertEquals(true, result)
    }
}