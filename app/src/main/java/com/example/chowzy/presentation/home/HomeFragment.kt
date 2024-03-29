package com.example.chowzy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.chowzy.R
import com.example.chowzy.data.datasource.category.CategoryDataSource
import com.example.chowzy.data.datasource.category.DummyCategoryDataSource
import com.example.chowzy.data.datasource.product.DummyFoodsDataSource
import com.example.chowzy.data.datasource.product.FoodsDataSource
import com.example.chowzy.data.model.Menu
import com.example.chowzy.databinding.FragmentHomeBinding
import com.example.chowzy.presentation.detailfood.DetailFoodActivity
import com.example.chowzy.presentation.home.adapter.CategoryAdapter
import com.example.chowzy.presentation.home.adapter.FoodsAdapter
import com.example.chowzy.presentation.home.adapter.OnItemClickedListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var foodsAdapter: FoodsAdapter? = null
    private var isUsingGridMode: Boolean = true
    private val foodsDataSource: FoodsDataSource by lazy {
        DummyFoodsDataSource()
    }
    private val categoriesDataSource: CategoryDataSource by lazy {
        DummyCategoryDataSource()
    }

    private val categoryAdapter = CategoryAdapter()

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
        bindFoodList(true)
        bindBanner()
        setListCategory()
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

    private fun setListCategory() {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(categoriesDataSource.getCategories())
    }

    private fun setClickAction() {
        binding.ibChangeMode.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            changeBtnIcon()
            bindFoodList(isUsingGridMode)
        }
    }

    private fun changeBtnIcon() {
        binding.ibChangeMode.setImageResource(if (isUsingGridMode) R.drawable.ic_grid else R.drawable.ic_list)
    }

    private fun bindFoodList(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) FoodsAdapter.MODE_GRID else FoodsAdapter.MODE_LIST
        foodsAdapter =
            FoodsAdapter(
                listMode = listMode,
                listener = object : OnItemClickedListener<Menu> {
                    override fun onItemClicked(item: Menu) {
                        navigateToDetail(item)
                    }
                },
            )
        binding.rvFoodList.apply {
            adapter = this@HomeFragment.foodsAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        foodsAdapter?.submitData(foodsDataSource.getFoods())
    }

    private fun navigateToDetail(item: Menu) {
        val navController = findNavController()
        val bundle = bundleOf(Pair(DetailFoodActivity.EXTRAS_ITEM, item))
        navController.navigate(R.id.action_foodListFragment_to_foodDetailActivity, bundle)
    }
}