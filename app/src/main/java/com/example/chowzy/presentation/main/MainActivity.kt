package com.example.chowzy.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chowzy.R
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.datasource.auth.FirebaseAuthDataSource
import com.example.chowzy.data.repository.user.UserRepository
import com.example.chowzy.data.repository.user.UserRepositoryImpl
import com.example.chowzy.data.source.firebase.FirebaseServices
import com.example.chowzy.data.source.firebase.FirebaseServicesImpl
import com.example.chowzy.databinding.ActivityMainBinding
import com.example.chowzy.presentation.auth.login.LoginActivity
import com.example.chowzy.utils.GenericViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        val service: FirebaseServices = FirebaseServicesImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(MainViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
//        testingCrashlytics()
    }

//    private fun testingCrashlytics() {
//        throw RuntimeException("Testing Crashlytics")
//    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.main_nav_host)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, args ->
            when (destination.id) {
                R.id.menu_tab_profile -> {
                    if(!viewModel.isLoggedIn()){
                        navigateToLogin()
                        controller.navigate(R.id.menu_tab_home)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}