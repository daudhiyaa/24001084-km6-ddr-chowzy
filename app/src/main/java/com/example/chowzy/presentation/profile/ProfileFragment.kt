package com.example.chowzy.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.datasource.auth.FirebaseAuthDataSource
import com.example.chowzy.data.repository.user.UserRepository
import com.example.chowzy.data.repository.user.UserRepositoryImpl
import com.example.chowzy.data.source.firebase.FirebaseServices
import com.example.chowzy.data.source.firebase.FirebaseServicesImpl
import com.example.chowzy.databinding.FragmentProfileBinding
import com.example.chowzy.presentation.auth.login.LoginActivity
import com.example.chowzy.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val service: FirebaseServices = FirebaseServicesImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindProfileData()
        setClickListener()
        observeEditMode()
    }

    private fun bindProfileData() {
        val profileData = viewModel.getProfile()
        binding.etName.setText(profileData?.name)
        binding.etEmail.setText(profileData?.email)
    }

    private fun setEditMode(isEnabledOrDisabledEdit: Boolean) {
        if (!isEnabledOrDisabledEdit) {
            binding.etName.isEnabled = false
            binding.etEmail.isEnabled = false
        } else {
            binding.etName.isEnabled = true
            binding.etEmail.isEnabled = true
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            setEditMode(it)
        }
    }

    private fun setClickListener() {
        binding.layoutProfileTopBar.ivEditProfile.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener {
            viewModel.doLogout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
