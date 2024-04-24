package com.example.chowzy.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.chowzy.R
import com.example.chowzy.databinding.ActivityLoginBinding
import com.example.chowzy.presentation.auth.register.RegisterActivity
import com.example.chowzy.presentation.main.MainActivity
import com.example.chowzy.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutLoginForm.btnLogin.setOnClickListener {
            inputLogin()
        }
        binding.layoutLoginForm.tvNotHaveAccountRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun inputLogin() {
        val email = binding.layoutLoginForm.etEmail.text.toString().trim()
        val password = binding.layoutLoginForm.etPassword.text.toString().trim()
        doLogin(email, password)
    }

    private fun doLogin(email: String, password: String) {
        loginViewModel.doLogin(email, password).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutLoginForm.pbLogin.isVisible = false
                    binding.layoutLoginForm.pbLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.layoutLoginForm.pbLogin.isVisible = true
                    binding.layoutLoginForm.pbLogin.isEnabled = false
                },
                doOnError = {
                    binding.layoutLoginForm.pbLogin.isVisible = false
                    binding.layoutLoginForm.pbLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed),
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

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }
}