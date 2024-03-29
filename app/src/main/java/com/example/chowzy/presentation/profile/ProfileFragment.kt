package com.example.chowzy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.chowzy.R
import com.example.chowzy.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

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
        setClickListener()
        observeEditMode()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.layoutProfileBody.ivProfilePicture.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_profile)
                transformations(CircleCropTransformation())
            }
            binding.layoutProfileBody.etName.setText(it.name)
            binding.layoutProfileBody.etUsername.setText(it.username)
            binding.layoutProfileBody.etEmail.setText(it.email)
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.layoutProfileBody.etUsername.isEnabled = it
            binding.layoutProfileBody.etEmail.isEnabled = it
        }
    }

    private fun setClickListener() {
        binding.layoutProfileHeader.ivEditProfile.setOnClickListener {
            viewModel.changeEditMode()
        }
    }
}
