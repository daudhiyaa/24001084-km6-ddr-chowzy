package com.example.chowzy.presentation.auth.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chowzy.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}