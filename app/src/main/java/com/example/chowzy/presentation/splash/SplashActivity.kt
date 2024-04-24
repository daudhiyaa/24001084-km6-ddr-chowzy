package com.example.chowzy.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.datasource.auth.FirebaseAuthDataSource
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.data.repository.auth.AuthRepositoryImpl
import com.example.chowzy.data.source.firebase.FirebaseServices
import com.example.chowzy.data.source.firebase.FirebaseServicesImpl
import com.example.chowzy.databinding.ActivitySplashBinding
import com.example.chowzy.presentation.main.MainActivity
import com.example.chowzy.utils.GenericViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashViewModel by viewModels {
        val service: FirebaseServices = FirebaseServicesImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: AuthRepository = AuthRepositoryImpl(dataSource)
        GenericViewModelFactory.create(SplashViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        directUser()
    }

    private fun directUser() {
        lifecycleScope.launch {
            delay(1500)
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}