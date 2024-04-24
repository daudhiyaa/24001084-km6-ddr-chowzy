package com.example.chowzy.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chowzy.databinding.FragmentProfileBinding
import com.example.chowzy.presentation.auth.login.LoginActivity
import com.example.chowzy.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

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
        val profileData = profileViewModel.getProfile()
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
        profileViewModel.isEditMode.observe(viewLifecycleOwner) {
            setEditMode(it)
        }
    }

    private fun setClickListener() {
        binding.layoutProfileTopBar.ivEditProfile.setOnClickListener {
            profileViewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener {
            profileViewModel.doLogout()
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
