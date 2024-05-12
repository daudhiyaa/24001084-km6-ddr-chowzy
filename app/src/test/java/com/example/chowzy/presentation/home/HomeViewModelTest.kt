package com.example.chowzy.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chowzy.MainCoroutineRule
import com.example.chowzy.data.model.User
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.preference.PreferenceRepository
import com.example.chowzy.getOrAwaitValue
import com.example.chowzy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    // khusus untuk viewmodel
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepo: CategoryRepository

    @MockK
    lateinit var menuRepo: MenuRepository

    @MockK
    lateinit var authRepo: AuthRepository

    @MockK
    lateinit var prefRepo: PreferenceRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { prefRepo.isUsingGridMode() } returns true
        every { prefRepo.setUsingGridMode(any()) } returns Unit
        viewModel = spyk(HomeViewModel(categoryRepo, menuRepo, authRepo, prefRepo))
    }

    @Test
    fun isUsingGridMode() {
        val actualValue = viewModel.getListMode()
        assertEquals(1, actualValue)
    }

    @Test
    fun getListMode() {
        val result = viewModel.getListMode()
        assertEquals(1, result)
        verify { prefRepo.isUsingGridMode() }
    }

    @Test
    fun changeListMode() {
        val currentValue = viewModel.isUsingGridMode.value ?: false
        val expectedNewValue = !currentValue

        viewModel.changeListMode()
        assertEquals(expectedNewValue, viewModel.isUsingGridMode.value)
        verify { prefRepo.isUsingGridMode() }
        verify { prefRepo.setUsingGridMode(any()) }
    }

    @Test
    fun getMenu() {
        every { menuRepo.getMenus(any()) } returns flow {
            emit(ResultWrapper.Success(listOf(mockk(), mockk())))
        }
        val result = viewModel.getMenu().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { menuRepo.getMenus() }
    }

    @Test
    fun getCategory() {
        every { categoryRepo.getCategories() } returns flow {
            emit(ResultWrapper.Success(listOf(mockk(), mockk())))
        }
        val result = viewModel.getCategories().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepo.getCategories() }
    }

    @Test
    fun getCurrentUser() {
        val user =
            User(
                "uuid",
                "Daud",
                "daud.dhiya31@gmail.com",
            )
        every { authRepo.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
        verify { authRepo.getCurrentUser() }
    }

    @Test
    fun isLoggedIn() {
        every { authRepo.isLoggedIn() } returns true
        val result = viewModel.isLoggedIn()
        assertEquals(true, result)
    }
}