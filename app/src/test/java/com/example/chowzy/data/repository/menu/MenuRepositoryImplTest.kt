package com.example.chowzy.data.repository.menu

import app.cash.turbine.test
import com.example.chowzy.data.datasource.menu.MenuDataSource
import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.source.network.model.checkout.CheckoutResponse
import com.example.chowzy.data.source.network.model.menu.MenuItemResponse
import com.example.chowzy.data.source.network.model.menu.MenusResponse
import com.example.chowzy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var menuDS: MenuDataSource
    private lateinit var menuRepo: MenuRepository

    private val globalUsername = "Daud"
    private val globalTotalPrice = 12
    private val globalMockMenu = listOf(
        MenuItemResponse(
            harga_format = "random",
            nama = "random",
            harga = 1000,
            image_url = "random",
            detail = "random",
            alamat_resto = "random",
        ),
        MenuItemResponse(
            harga_format = "random",
            nama = "random",
            harga = 2000,
            image_url = "random",
            detail = "random",
            alamat_resto = "random",
        ),
    )
    private val globalMockCart = listOf(
        Cart(
            id = 1,
            productId = "random",
            productName = "random",
            productImgUrl = "random",
            productPrice = 1000,
            itemQuantity = 1,
            itemNotes = "random",
        ),
        Cart(
            id = 2,
            productId = "random",
            productName = "random",
            productImgUrl = "random",
            productPrice = 2000,
            itemQuantity = 2,
            itemNotes = "random",
        ),
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuRepo = MenuRepositoryImpl(menuDS)
    }

    @Test
    fun `get catalog success`() {
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns globalMockMenu

        runTest {
            coEvery { menuDS.getMenuData(any()) } returns mockResponse
            menuRepo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { menuDS.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get catalog loading`() {
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns globalMockMenu

        runTest {
            coEvery { menuDS.getMenuData(any()) } returns mockResponse
            menuRepo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { menuDS.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get catalog error`() {
        runTest {
            coEvery { menuDS.getMenuData(any()) } throws IllegalStateException("Mock error")
            menuRepo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { menuDS.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get catalog empty`() {
        val mockListMenu = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockListMenu

        runTest {
            coEvery { menuDS.getMenuData(any()) } returns mockResponse
            menuRepo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { menuDS.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `create order success`() {
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)
        runTest {
            coEvery { menuDS.createOrder(any()) } returns mockResponse
            menuRepo.createOrder(globalUsername, globalMockCart, globalTotalPrice).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { menuDS.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order loading`() {
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)

        runTest {
            coEvery { menuDS.createOrder(any()) } returns mockResponse
            menuRepo.createOrder(globalUsername, globalMockCart, globalTotalPrice).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { menuDS.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order error`() {
        runTest {
            coEvery { menuDS.createOrder(any()) } throws IllegalStateException("Mock error")
            menuRepo.createOrder(globalUsername, globalMockCart, globalTotalPrice).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { menuDS.createOrder(any()) }
            }
        }
    }
}