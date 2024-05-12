package com.example.chowzy.data.repository.cart

import app.cash.turbine.test
import com.example.chowzy.data.datasource.cart.CartDataSource
import com.example.chowzy.data.model.Cart
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.model.PriceItem
import com.example.chowzy.data.source.local.database.entity.CartEntity
import com.example.chowzy.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var dataSource: CartDataSource
    private lateinit var repository: CartRepository

    private val globalMockListCartEntity = listOf(
        CartEntity(
            id = 1,
            productId = "random",
            productName = "random",
            productImgUrl = "random",
            productPrice = 1000,
            itemQuantity = 1,
            itemNotes = "random",
        ),
        CartEntity(
            id = 2,
            productId = "random",
            productName = "random",
            productImgUrl = "random",
            productPrice = 2000,
            itemQuantity = 2,
            itemNotes = "random",
        )
    )
    private val globalMockListPriceItem = listOf(
        PriceItem(
            name = "random",
            total = 3000,
        ),
        PriceItem(
            name = "random",
            total = 6000,
        )
    )
    private val globalMockCart = Cart(
        id = 1,
        productId = "random",
        productName = "random",
        productImgUrl = "random",
        productPrice = 1000,
        itemQuantity = 1,
        itemNotes = "random",
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(dataSource)
    }

    @Test
    fun `get cart success`() {
        val mockFlow =
            flow {
                emit(globalMockListCartEntity)
            }
        every { dataSource.getAllCarts() } returns mockFlow

        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(globalMockListCartEntity.size, actualData.payload?.first?.size)
                assertEquals(5000, actualData.payload?.second)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get cart loading`() {
        val mockFlow =
            flow {
                emit(globalMockListCartEntity)
            }
        every { dataSource.getAllCarts() } returns mockFlow

        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get cart error`() {
        every { dataSource.getAllCarts() } returns
                flow {
                    throw IllegalStateException("Error")
                }
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get cart empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data success`() {
        val mockFlow =
            flow {
                emit(globalMockListCartEntity)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(globalMockListCartEntity.size, actualData.payload?.first?.size)
                assertEquals(globalMockListPriceItem.size, actualData.payload?.second?.size)
                assertEquals(5000, actualData.payload?.third)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data loading`() {
        val mockFlow =
            flow {
                emit(globalMockListCartEntity)
            }
        every { dataSource.getAllCarts() } returns mockFlow

        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(1110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data error`() {
        every { dataSource.getAllCarts() } returns
                flow {
                    throw IllegalStateException("Checkout Error")
                }

        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get checkout data empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow

        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `create cart success`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "1"
        coEvery { dataSource.insertCart(any()) } returns 1

        runTest {
            /* if notes = null */
            // repository.createCart(mockMenu, 1)
            repository.createCart(mockMenu, 1, "random")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(701)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `create cart loading`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "1"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockMenu, 1, "random")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Loading)
                    coVerify { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `create cart error process`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "1"
        /* error product ID */
        // every { mockMenu.id } returns null
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("Create Cart Error")

        runTest {
            repository.createCart(mockMenu, 1, "random")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify { dataSource.insertCart(any()) }
                    /* error product ID */
                    // coVerify(atLeast = 0) { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `decrease cart more than 0`() {
        val mockCart =
            Cart(
                id = 1,
                productId = "random",
                productName = "random",
                productImgUrl = "random",
                productPrice = 2000,
                itemQuantity = 2,
                itemNotes = "random",
            )

        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1

        runTest {
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 0) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart less than 1`() {
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1

        runTest {
            repository.decreaseCart(globalMockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 0) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `increase cart more than 0`() {
        coEvery { dataSource.updateCart(any()) } returns 1

        runTest {
            repository.increaseCart(globalMockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.setCartNotes(globalMockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
        coEvery { dataSource.deleteCart(any()) } returns 1
        runTest {
            repository.deleteCart(globalMockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
            }
        }
    }
}