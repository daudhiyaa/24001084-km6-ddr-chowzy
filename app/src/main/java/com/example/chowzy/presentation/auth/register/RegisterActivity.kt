package com.example.chowzy.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.chowzy.R
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.datasource.auth.FirebaseAuthDataSource
import com.example.chowzy.data.repository.user.UserRepository
import com.example.chowzy.data.repository.user.UserRepositoryImpl
import com.example.chowzy.data.source.firebase.FirebaseServices
import com.example.chowzy.data.source.firebase.FirebaseServicesImpl
import com.example.chowzy.databinding.ActivityRegisterBinding
import com.example.chowzy.presentation.auth.login.LoginActivity
import com.example.chowzy.presentation.main.MainActivity
import com.example.chowzy.utils.GenericViewModelFactory
import com.example.chowzy.utils.proceedWhen

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels {
        val service: FirebaseServices = FirebaseServicesImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(RegisterViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutRegisterForm.btnRegister.setOnClickListener {
            inputRegister()
        }
        binding.layoutRegisterForm.tvHaveAccount.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun inputRegister() {
        val name = binding.layoutRegisterForm.etNameRegister.text.toString().trim()
        val email = binding.layoutRegisterForm.etEmailRegister.text.toString().trim()
        val password = binding.layoutRegisterForm.etPasswordRegister.text.toString().trim()
        doRegister(name, email, password)
    }

    private fun doRegister(name: String, email: String, password: String) {
        viewModel.doRegister(name, email, password).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutRegisterForm.pbRegister.isVisible = false
                    binding.layoutRegisterForm.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.register_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToLogin()
                },
                doOnLoading = {
                    binding.layoutRegisterForm.pbRegister.isVisible = true
                    binding.layoutRegisterForm.btnRegister.isEnabled = false
                },
                doOnError = {
                    binding.layoutRegisterForm.pbRegister.isVisible = false
                    binding.layoutRegisterForm.btnRegister.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.register_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }
}