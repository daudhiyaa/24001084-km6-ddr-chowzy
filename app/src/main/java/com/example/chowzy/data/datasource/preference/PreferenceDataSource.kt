package com.example.chowzy.data.datasource.preference

interface PreferenceDataSource {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}