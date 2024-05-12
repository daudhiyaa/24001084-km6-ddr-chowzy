package com.example.chowzy.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chowzy.MainCoroutineRule
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.data.repository.cart.CartRepository
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
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    // khusus untuk testing viewmodel
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepo: CartRepository

    @MockK
    lateinit var authRepo: AuthRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(cartRepo, authRepo))
    }

    @Test
    fun getAllCarts() {
        every { cartRepo.getUserCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(listOf(mockk(relaxed = true), mockk(relaxed = true)), 8000)
                )
            )
        }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000, result.payload?.second)
    }

    @Test
    fun decreaseCart() {
        every { cartRepo.decreaseCart(any()) } returns flow {
            emit(ResultWrapper.Success(true))
        }
        viewModel.decreaseCart(mockk())
        verify { cartRepo.decreaseCart(any()) }
    }

    @Test
    fun increaseCart() {
        every { cartRepo.increaseCart(any()) } returns flow {
            emit(ResultWrapper.Success(true))
        }
        viewModel.increaseCart(mockk())
        verify { cartRepo.increaseCart(any()) }
    }

    @Test
    fun removeCart() {
        every { cartRepo.deleteCart(any()) } returns flow {
            emit(ResultWrapper.Success(true))
        }
        viewModel.removeCart(mockk())
        verify { cartRepo.deleteCart(any()) }
    }

    @Test
    fun setCartNotes() {
        every { cartRepo.setCartNotes(any()) } returns flow {
            emit(ResultWrapper.Success(true))
        }
        viewModel.setCartNotes(mockk())
        verify { cartRepo.setCartNotes(any()) }
    }

    @Test
    fun isLoggedIn() {
        every { authRepo.isLoggedIn() } returns true
        val result = viewModel.isLoggedIn()
        assertEquals(true, result)
    }
}