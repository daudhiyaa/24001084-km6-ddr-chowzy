package com.example.chowzy.data.repository.preference

import com.example.chowzy.data.datasource.preference.PreferenceDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PreferenceRepositoryImplTest {
    @MockK
    lateinit var prefDataSource: PreferenceDataSource

    private lateinit var prefRepo: PreferenceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        prefRepo = PreferenceRepositoryImpl(prefDataSource)
    }

    @Test
    fun isUsingGridMode() {
        runTest {
            every { prefRepo.isUsingGridMode() } returns true
            val actualResult = prefDataSource.isUsingGridMode()
            verify { prefRepo.isUsingGridMode() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun setUsingGridMode() {
        runTest {
            val isUsingGridMode = true
            every { prefRepo.setUsingGridMode(isUsingGridMode) } returns Unit
            val actualResult = prefDataSource.setUsingGridMode(isUsingGridMode)
            verify { prefRepo.setUsingGridMode(isUsingGridMode) }
            assertEquals(Unit, actualResult)
        }
    }
}