package com.example.chowzy.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.chowzy.R
import com.example.chowzy.data.datasource.cart.CartDataSource
import com.example.chowzy.data.datasource.cart.CartDatabaseDataSource
import com.example.chowzy.data.model.Menu
import com.example.chowzy.data.repository.cart.CartRepository
import com.example.chowzy.data.repository.cart.CartRepositoryImpl
import com.example.chowzy.data.source.local.database.AppDatabase
import com.example.chowzy.databinding.ActivityDetailBinding
import com.example.chowzy.utils.GenericViewModelFactory
import com.example.chowzy.utils.proceedWhen
import com.example.chowzy.utils.toRupiahFormat

class DetailFoodActivity : AppCompatActivity() {
    private val urlMaps: String = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"

    private val viewModel: DetailFoodViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val cartRepo: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailFoodViewModel(intent?.extras, cartRepo)
        )
    }

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.food?.let { bindDetailFood(it) }
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.layoutDetailContent.layoutDetailBanner.btnBack.setOnClickListener {
            finish()
        }
        binding.layoutDetailContent.layoutDetailLocation.tvLocationDesc.setOnClickListener {
            openMaps()
        }
        binding.layoutAddToCart.let {
            it.btnIncrement.setOnClickListener {
                viewModel.add()
            }
            it.btnDecrement.setOnClickListener {
                viewModel.minus()
            }
            it.btnAddToCart.setOnClickListener {
                addToCart()
            }
        }
    }

    private fun bindDetailFood(data: Menu) {
        /* DETAIL FOOD */
        binding.layoutDetailContent.layoutDetailFood.let {
            it.tvDetailFoodName.text = data.name
            it.tvDetailFoodPrice.text = data.price.toRupiahFormat()
            it.tvDetailFoodDesc.text = data.desc
        }

        /* DETAIL BANNER */
        binding.layoutDetailContent.layoutDetailBanner.ivBannerDetail.load(data.imgUrl) {
            crossfade(true)
        }

        /* DETAIL LOCATION */
        binding.layoutDetailContent.layoutDetailLocation.tvLocationDesc.text = data.location
    }

    private fun observeData() {
        viewModel.totalPriceLiveData.observe(this) {
            binding.layoutAddToCart.btnAddToCart.isEnabled = it != 0.0
            binding.layoutAddToCart.btnAddToCart.text =
                getString(R.string.add_to_cart_btn, it.toRupiahFormat())
        }
        viewModel.foodQtyLiveData.observe(this) {
            binding.layoutAddToCart.tvQtyFood.text = it.toString()
        }
    }

    private fun addToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_successfully_add_to_cart), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_failed_add_to_cart), Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun openMaps() {
        val mapsURL = Uri.parse(urlMaps)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapsURL)
        startActivity(mapIntent)
    }

    companion object {
        const val EXTRAS_ITEM = "EXTRAS_ITEM"
        fun startActivity(context: Context, food: Menu) {
            val intent = Intent(context, DetailFoodActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, food)
            context.startActivity(intent)
        }
    }
}