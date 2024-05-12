package com.example.chowzy.data.datasource.preference

import android.content.SharedPreferences
import com.example.chowzy.data.source.local.preference.UserPreference
import com.example.chowzy.utils.SharedPreferenceUtils.set

class PreferenceDataSourceImpl(private val userPreference: UserPreference) : PreferenceDataSource {
    override fun isUsingGridMode(): Boolean {
        return userPreference.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        return userPreference.setUsingGridMode(isUsingGridMode)
    }
}