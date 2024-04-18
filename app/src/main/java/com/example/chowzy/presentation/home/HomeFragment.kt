package com.example.chowzy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.chowzy.R
import com.example.chowzy.data.datasource.category.CategoryApiDataSource
import com.example.chowzy.data.datasource.menu.MenuApiDataSource
import com.example.chowzy.data.model.Category
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.category.CategoryRepositoryImpl
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.menu.MenuRepositoryImpl
import com.example.chowzy.data.source.local.preference.UserPreferenceImpl
import com.example.chowzy.data.source.network.services.RestaurantApiService
import com.example.chowzy.databinding.FragmentHomeBinding
import com.example.chowzy.presentation.detailmenu.DetailMenuActivity
import com.example.chowzy.presentation.home.adapter.CategoryAdapter
import com.example.chowzy.presentation.home.adapter.MenuAdapter
import com.example.chowzy.utils.GenericViewModelFactory
import com.example.chowzy.utils.proceedWhen


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = RestaurantApiService.invoke()
        val userPreference = UserPreferenceImpl(requireContext())
        val menuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(
            HomeViewModel(
                categoryRepository,
                menuRepository,
                userPreference
            )
        )
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData(it.name)
        }
    }
    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(viewModel.getListMode()) {
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
        setupCategory()
        setupMenu()
        bindBanner()
        observeGridMode()
        getCategoryData()
        getMenuData(null)
        setClickAction()
    }

    private fun setClickAction() {
        binding.ibChangeMode.setOnClickListener {
            viewModel.changeListMode()
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
        viewModel.getMenu(name).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                }
            )
        }
    }

    private fun getCategoryData() {
        viewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                }
            )
        }
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
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

    private fun bindMenuList(data : List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }
}