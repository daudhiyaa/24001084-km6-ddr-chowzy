package com.example.chowzy.di

import android.content.SharedPreferences
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.datasource.auth.FirebaseAuthDataSource
import com.example.chowzy.data.datasource.cart.CartDataSource
import com.example.chowzy.data.datasource.cart.CartDatabaseDataSource
import com.example.chowzy.data.datasource.category.CategoryApiDataSource
import com.example.chowzy.data.datasource.category.CategoryDataSource
import com.example.chowzy.data.datasource.menu.MenuApiDataSource
import com.example.chowzy.data.datasource.menu.MenuDataSource
import com.example.chowzy.data.datasource.preference.PreferenceDataSource
import com.example.chowzy.data.datasource.preference.PreferenceDataSourceImpl
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.data.repository.auth.AuthRepositoryImpl
import com.example.chowzy.data.repository.cart.CartRepository
import com.example.chowzy.data.repository.cart.CartRepositoryImpl
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.category.CategoryRepositoryImpl
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.menu.MenuRepositoryImpl
import com.example.chowzy.data.repository.preference.PreferenceRepository
import com.example.chowzy.data.repository.preference.PreferenceRepositoryImpl
import com.example.chowzy.data.source.firebase.FirebaseServices
import com.example.chowzy.data.source.firebase.FirebaseServicesImpl
import com.example.chowzy.data.source.local.database.AppDatabase
import com.example.chowzy.data.source.local.database.dao.CartDao
import com.example.chowzy.data.source.network.services.RestaurantApiService
import com.example.chowzy.presentation.auth.login.LoginViewModel
import com.example.chowzy.presentation.auth.register.RegisterViewModel
import com.example.chowzy.presentation.cart.CartViewModel
import com.example.chowzy.presentation.checkout.CheckoutViewModel
import com.example.chowzy.presentation.detailmenu.DetailMenuViewModel
import com.example.chowzy.presentation.home.HomeViewModel
import com.example.chowzy.presentation.main.MainViewModel
import com.example.chowzy.presentation.profile.ProfileViewModel
import com.example.chowzy.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule = module {
        single<RestaurantApiService> { RestaurantApiService.invoke() }
    }

    private val firebaseModule = module {
        single<FirebaseAuth> { FirebaseAuth.getInstance() }
        single<FirebaseServices> { FirebaseServicesImpl(get()) }
    }

    private val localModule = module {
        single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
        single<CartDao> { get<AppDatabase>().cartDao() }
        single<SharedPreferences> {
            SharedPreferenceUtils.createPreference(
                androidContext(),
                PreferenceDataSourceImpl.PREF_NAME
            )
        }
        single<PreferenceDataSource> { PreferenceDataSourceImpl(get()) }
    }

    private val datasourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<CategoryDataSource> { CategoryApiDataSource(get()) }
        single<MenuDataSource> { MenuApiDataSource(get()) }
        single<AuthDataSource> { FirebaseAuthDataSource(get()) }
        single<PreferenceDataSource> { PreferenceDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get()) }
        single<CategoryRepository> { CategoryRepositoryImpl(get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
        single<AuthRepository> { AuthRepositoryImpl(get()) }
        single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
    }

    private val viewmodelModule = module {
        viewModelOf(::HomeViewModel)
        viewModel { params ->
            DetailMenuViewModel(
                extras = params.get(),
                cartRepository = get()
            )
        }
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::CartViewModel)
    }

    val modules = listOf<Module>(
        networkModule,
        localModule,
        firebaseModule,
        datasourceModule,
        repositoryModule,
        viewmodelModule
    )
}