package com.example.chowzy.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.chowzy.R
import com.example.chowzy.data.model.Menu
import com.example.chowzy.databinding.ActivityDetailBinding
import com.example.chowzy.utils.proceedWhen
import com.example.chowzy.utils.toRupiahFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    private val urlMaps: String = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"

    private val viewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.menu?.let { bindDetailMenu(it) }
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

    private fun bindDetailMenu(data: Menu) {
        /* DETAIL FOOD */
        binding.layoutDetailContent.layoutDetailMenu.let {
            it.tvDetailMenuName.text = data.name
            it.tvDetailMenuPrice.text = data.price.toRupiahFormat()
            it.tvDetailMenuDesc.text = data.desc
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
            binding.layoutAddToCart.btnAddToCart.isEnabled = it != 0
            binding.layoutAddToCart.btnAddToCart.text =
                getString(R.string.add_to_cart_btn, it.toRupiahFormat())
        }
        viewModel.menuQtyLiveData.observe(this) {
            binding.layoutAddToCart.tvQtyMenu.text = it.toString()
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
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, menu)
            context.startActivity(intent)
        }
    }
}