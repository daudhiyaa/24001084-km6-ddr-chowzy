package com.example.chowzy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.chowzy.R
import com.example.chowzy.data.model.Category
import com.example.chowzy.data.model.Menu
import com.example.chowzy.databinding.FragmentHomeBinding
import com.example.chowzy.presentation.detailmenu.DetailMenuActivity
import com.example.chowzy.presentation.home.adapter.CategoryAdapter
import com.example.chowzy.presentation.home.adapter.MenuAdapter
import com.example.chowzy.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData(it.name)
        }
    }
    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(homeViewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserName()
        setupCategory()
        setupMenu()
        bindBanner()
        observeGridMode()
        getCategoryData()
        getMenuData(null)
        setClickAction()
    }

    private fun setUserName() {
        if (homeViewModel.isLoggedIn()) {
            val username = homeViewModel.getCurrentUser()?.name
            binding.layoutHeader.tvGreeting.text = getString(R.string.hi_name, username)
        } else {
            binding.layoutHeader.tvGreeting.text = getString(R.string.hi_guest)
        }
    }

    private fun setClickAction() {
        binding.ibChangeMode.setOnClickListener {
            homeViewModel.changeListMode()
        }
    }

    private fun setupCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun setupMenu() {
        binding.rvMenuList.apply {
            adapter = menuAdapter
        }
    }

    private fun bindBanner() {
        binding.layoutBanner.let {
            it.ivBgBanner.load("https://github.com/daudhiyaa/chowzy-assets/blob/main/banner/bg_banner.jpg?raw=true") {
                crossfade(true)
            }
            it.ivIconBanner.load("https://github.com/daudhiyaa/chowzy-assets/blob/main/banner/discount.png?raw=true") {
                crossfade(true)
            }
        }
    }

    private fun getMenuData(name: String? = null) {
        homeViewModel.getMenu(name).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(requireContext(), "Failed Bind List Menu", Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun getCategoryData() {
        homeViewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    it.payload?.let { data -> bindCategory(data) }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(requireContext(), "Failed Bind List Category", Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun observeGridMode() {
        homeViewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            changeBtnIcon(isUsingGridMode)
            changeLayout(isUsingGridMode)
        }
    }

    private fun changeLayout(usingGridMode: Boolean) {
        val listMode = if (usingGridMode) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        menuAdapter.updateListMode(listMode)
        setupMenu()
        binding.rvMenuList.apply {
            layoutManager = GridLayoutManager(requireContext(), if (usingGridMode) 2 else 1)
        }
    }

    private fun changeBtnIcon(usingGridMode: Boolean) {
        binding.ibChangeMode.setImageResource(if (usingGridMode) R.drawable.ic_list else R.drawable.ic_grid)
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(data: List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }
}