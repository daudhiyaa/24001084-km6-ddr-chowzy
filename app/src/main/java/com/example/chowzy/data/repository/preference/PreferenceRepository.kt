package com.example.chowzy.data.repository.preference

interface PreferenceRepository {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}