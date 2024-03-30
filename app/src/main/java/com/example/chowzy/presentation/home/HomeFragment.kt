package com.example.chowzy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.chowzy.R
import com.example.chowzy.data.datasource.category.DummyCategoryDataSource
import com.example.chowzy.data.datasource.product.DummyMenusDataSource
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.repository.category.CategoryRepository
import com.example.chowzy.data.repository.category.CategoryRepositoryImpl
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.menu.MenuRepositoryImpl
import com.example.chowzy.databinding.FragmentHomeBinding
import com.example.chowzy.presentation.detailmenu.DetailMenuActivity
import com.example.chowzy.presentation.home.adapter.CategoryAdapter
import com.example.chowzy.presentation.home.adapter.MenusAdapter
import com.example.chowzy.presentation.home.adapter.OnItemClickedListener
import com.example.chowzy.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenusDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(menuRepository, categoryRepository))
    }

    private var isUsingGridMode: Boolean = true

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {

        }
    }
    private var menusAdapter: MenusAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindBanner()
        bindListMenu(true)
        bindListCategory()
        setClickAction()
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

    private fun bindListCategory() {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(viewModel.getCategories())
    }

    private fun setClickAction() {
        binding.ibChangeMode.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            changeBtnIcon()
            bindListMenu(isUsingGridMode)
        }
    }

    private fun changeBtnIcon() {
        binding.ibChangeMode.setImageResource(if (isUsingGridMode) R.drawable.ic_grid else R.drawable.ic_list)
    }

    private fun bindListMenu(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenusAdapter.MODE_GRID else MenusAdapter.MODE_LIST
        menusAdapter =
            MenusAdapter(
                listMode = listMode,
                listener = object : OnItemClickedListener<Menu> {
                    override fun onItemClicked(item: Menu) {
                        navigateToDetail(item)
                    }
                },
            )
        binding.rvMenuList.apply {
            adapter = this@HomeFragment.menusAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        menusAdapter?.submitData(viewModel.getMenus())
    }

    private fun navigateToDetail(item: Menu) {
        val navController = findNavController()
        val bundle = bundleOf(Pair(DetailMenuActivity.EXTRAS_ITEM, item))
        navController.navigate(R.id.action_menuListFragment_to_menuDetailActivity, bundle)
    }
}