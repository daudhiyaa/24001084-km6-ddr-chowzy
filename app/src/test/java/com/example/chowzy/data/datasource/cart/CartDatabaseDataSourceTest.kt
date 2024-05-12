package com.example.chowzy.data.datasource.cart

import app.cash.turbine.test
import com.example.chowzy.data.source.local.database.dao.CartDao
import com.example.chowzy.data.source.local.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CartDatabaseDataSourceTest {

    @MockK
    lateinit var cartDao: CartDao

    private lateinit var cartDataSource: CartDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartDataSource = CartDatabaseDataSource(cartDao)
    }

    @Test
    fun getAllCarts() {
        val entity1 = mockk<CartEntity>()
        val entity2 = mockk<CartEntity>()
        val listEntity = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(listEntity)
            }

        every { cartDao.getAllCarts() } returns mockFlow
        runTest {
            cartDataSource.getAllCarts().test {
                val result = awaitItem()
                assertEquals(listEntity, result)
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun insertCart() {
        val entity = mockk<CartEntity>()
        coEvery { cartDao.insertCart(any()) } returns 1

        runTest {
            val result = cartDataSource.insertCart(entity)
            coVerify { cartDao.insertCart(entity) }
            assertEquals(1, result)
        }
    }

    @Test
    fun deleteCart() {
        val entity = mockk<CartEntity>()
        coEvery { cartDao.deleteCart(any()) } returns 1

        runTest {
            val result = cartDataSource.deleteCart(entity)
            coVerify { cartDao.deleteCart(entity) }
            assertEquals(1, result)
        }
    }

    @Test
    fun updateCart() {
        val entity = mockk<CartEntity>()
        coEvery { cartDao.updateCart(any()) } returns 1

        runTest {
            val result = cartDataSource.updateCart(entity)
            coVerify { cartDao.updateCart(entity) }
            assertEquals(1, result)
        }
    }

    @Test
    fun deleteAll() {
        coEvery { cartDao.deleteAll() } returns Unit

        runTest {
            val result = cartDataSource.deleteAll()
            coVerify { cartDao.deleteAll() }
            assertEquals(Unit, result)
        }
    }
}