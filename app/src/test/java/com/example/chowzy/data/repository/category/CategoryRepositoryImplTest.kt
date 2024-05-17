package com.example.chowzy.data.repository.category

import app.cash.turbine.test
import com.example.chowzy.data.datasource.category.CategoryDataSource
import com.example.chowzy.data.source.network.model.category.CategoriesResponse
import com.example.chowzy.data.source.network.model.category.CategoryItemResponse
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
import java.lang.IllegalStateException

class CategoryRepositoryImplTest {
    @MockK
    lateinit var categoryDataSource: CategoryDataSource
    private lateinit var categoryRepository: CategoryRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        categoryRepository = CategoryRepositoryImpl(categoryDataSource)
    }

    @Test
    fun `get categories loading`() {
        val c1 = CategoryItemResponse(image_url = "url1", nama = "nama1")
        val c2 = CategoryItemResponse(image_url = "url2", nama = "nama2")
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()

        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { categoryDataSource.getCategoryData() } returns mockResponse
            categoryRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { categoryDataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun `get categories success`() {
        val c1 = CategoryItemResponse(image_url = "url1", nama = "nama1")
        val c2 = CategoryItemResponse(image_url = "url2", nama = "nama2")
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()

        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { categoryDataSource.getCategoryData() } returns mockResponse
            categoryRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { categoryDataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun `get categories error`() {
        runTest {
            coEvery { categoryDataSource.getCategoryData() } throws IllegalStateException("Mock Error")
            categoryRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { categoryDataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun `get categories empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory

        runTest {
            coEvery { categoryDataSource.getCategoryData() } returns mockResponse
            categoryRepository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { categoryDataSource.getCategoryData() }
            }
        }
    }
}