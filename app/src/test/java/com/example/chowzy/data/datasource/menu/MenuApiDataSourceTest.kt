package com.example.chowzy.data.datasource.menu

import com.example.chowzy.data.source.network.model.checkout.CheckoutRequestResponse
import com.example.chowzy.data.source.network.model.checkout.CheckoutResponse
import com.example.chowzy.data.source.network.model.menu.MenusResponse
import com.example.chowzy.data.source.network.services.RestaurantApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {

    @MockK
    lateinit var  service: RestaurantApiService
    
    private lateinit var menuDataSource: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuDataSource = MenuApiDataSource(service)
    }

    @Test
    fun getMenuData() {
        runTest {
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val actualResponse = menuDataSource.getMenuData("anything")
            coVerify { service.getMenus(any()) }
            assertEquals(mockResponse, actualResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestResponse>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = menuDataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(mockResponse, actualResponse)
        }
    }
}