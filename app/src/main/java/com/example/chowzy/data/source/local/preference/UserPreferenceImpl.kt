package com.example.chowzy.data.source.local.preference

import android.content.Context
import com.example.chowzy.utils.SharedPreferenceUtils
import com.example.chowzy.utils.SharedPreferenceUtils.set

class UserPreferenceImpl(private val context : Context) : UserPreference {
    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)
    override fun isUsingGridMode(): Boolean = pref.getBoolean(KEY_IS_USING_GRID_MODE, false)

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        pref[KEY_IS_USING_GRID_MODE] = isUsingGridMode
    }

    companion object{
        const val PREF_NAME = "chowzy-pref"
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
    }
}