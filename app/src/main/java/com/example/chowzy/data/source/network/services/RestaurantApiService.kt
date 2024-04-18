package com.example.chowzy.data.source.network.services

import com.example.chowzy.BuildConfig
import com.example.chowzy.data.source.network.model.category.CategoriesResponse
import com.example.chowzy.data.source.network.model.menu.MenusResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantApiService {
    @GET("listmenu") // endpoint
    suspend fun getMenus(@Query("c") category: String? = null): MenusResponse

    @GET("category") // endpoint
    suspend fun getCategories(): CategoriesResponse

    companion object {
        @JvmStatic
        operator fun invoke(): RestaurantApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RestaurantApiService::class.java)
        }
    }
}