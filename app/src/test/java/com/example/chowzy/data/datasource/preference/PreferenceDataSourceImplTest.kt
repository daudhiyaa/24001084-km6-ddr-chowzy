package com.example.chowzy.data.datasource.preference

import com.example.chowzy.data.source.local.preference.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PreferenceDataSourceImplTest {
    @MockK
    lateinit var userPref: UserPreference
    lateinit var prefDataSource: PreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        prefDataSource = PreferenceDataSourceImpl(userPref)
    }

    @Test
    fun isUsingGridMode() {
        runTest {
            every { userPref.isUsingGridMode() } returns true
            val result = prefDataSource.isUsingGridMode()
            verify { userPref.isUsingGridMode() }
            assertEquals(true, result)
        }
    }

    @Test
    fun setUsingGridMode() {
        runTest {
            val setUsingGridMode = true
            every { userPref.setUsingGridMode(setUsingGridMode) } returns Unit
            val result = prefDataSource.setUsingGridMode(setUsingGridMode)
            verify { userPref.setUsingGridMode(setUsingGridMode) }
            assertEquals(Unit, result)
        }
    }
}