package com.example.chowzy.data.datasource.category

import com.example.chowzy.data.source.network.model.category.CategoriesResponse
import com.example.chowzy.data.source.network.services.RestaurantApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CategoryApiDataSourceTest {

    @MockK
    lateinit var  service: RestaurantApiService

    private lateinit var categoryDataSource: CategoryDataSource
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        categoryDataSource = CategoryApiDataSource(service)
    }

    @Test
    fun getCategoryData() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val actualResult = categoryDataSource.getCategoryData()
            coEvery { service.getCategories() }
            assertEquals(mockResponse, actualResult)
        }
    }
}