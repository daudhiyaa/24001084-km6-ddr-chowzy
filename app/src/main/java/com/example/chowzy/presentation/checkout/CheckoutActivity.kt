package com.example.chowzy.presentation.checkout

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.chowzy.R
import com.example.chowzy.data.datasource.auth.AuthDataSource
import com.example.chowzy.data.datasource.auth.FirebaseAuthDataSource
import com.example.chowzy.data.datasource.cart.CartDataSource
import com.example.chowzy.data.datasource.cart.CartDatabaseDataSource
import com.example.chowzy.data.datasource.menu.MenuApiDataSource
import com.example.chowzy.data.datasource.menu.MenuDataSource
import com.example.chowzy.data.repository.cart.CartRepository
import com.example.chowzy.data.repository.cart.CartRepositoryImpl
import com.example.chowzy.data.repository.menu.MenuRepository
import com.example.chowzy.data.repository.menu.MenuRepositoryImpl
import com.example.chowzy.data.repository.auth.AuthRepository
import com.example.chowzy.data.repository.auth.AuthRepositoryImpl
import com.example.chowzy.data.source.firebase.FirebaseServices
import com.example.chowzy.data.source.firebase.FirebaseServicesImpl
import com.example.chowzy.data.source.local.database.AppDatabase
import com.example.chowzy.data.source.network.services.RestaurantApiService
import com.example.chowzy.databinding.ActivityCheckoutBinding
import com.example.chowzy.presentation.auth.login.LoginActivity
import com.example.chowzy.presentation.cart.adapter.CartListAdapter
import com.example.chowzy.presentation.checkout.adapter.PriceListAdapter
import com.example.chowzy.utils.GenericViewModelFactory
import com.example.chowzy.utils.proceedWhen
import com.example.chowzy.utils.toRupiahFormat

class CheckoutActivity : AppCompatActivity() {
    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val firebaseService: FirebaseServices = FirebaseServicesImpl()
        val firebaseDataSource: AuthDataSource = FirebaseAuthDataSource(firebaseService)
        val firebaseRepo: AuthRepository = AuthRepositoryImpl(firebaseDataSource)

        val appDB = AppDatabase.createInstance(this)
        val cartDataSource: CartDataSource = CartDatabaseDataSource(appDB.cartDao())
        val cartRepo: CartRepository = CartRepositoryImpl(cartDataSource)

        val apiService = RestaurantApiService.invoke()
        val menuDataSource: MenuDataSource = MenuApiDataSource(apiService)
        val menuRepo: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(cartRepo, firebaseRepo, menuRepo))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        setClickListeners()
        observeData()
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            if (viewModel.isLoggedIn()) {
                observeCheckoutResult()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun observeCheckoutResult() {
        viewModel.checkoutCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    viewModel.removeAllCart()
                    showDialog(this)
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, getString(R.string.failed_to_checkout), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun showDialog(context: Context) {
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.dialog_checkout, null)
        val finishBtn = dialogView.findViewById<Button>(R.id.btn_back_home)
        val alertDialogBuilder = AlertDialog.Builder(context)
        val dialog = alertDialogBuilder.create()
        alertDialogBuilder.setView(dialogView)
        finishBtn.setOnClickListener {
            viewModel.removeAllCart()
            (context as? Activity)?.finish() // Make sure context is an Activity
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = true
                    binding.layoutContent.rvCart.isVisible = true
                    binding.cvSectionOrder.isVisible = true
                    result.payload?.let { (carts, priceItems, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toRupiahFormat()
                        priceItemAdapter.submitData(priceItems)
                    }
                }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }, doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }, doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, _, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toRupiahFormat()
                    }
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }
            )
        }
    }
}