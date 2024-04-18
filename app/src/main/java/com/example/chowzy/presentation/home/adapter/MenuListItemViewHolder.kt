package com.example.chowzy.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.chowzy.core.ViewHolderBinder
import com.example.chowzy.data.model.Menu
import com.example.chowzy.databinding.ItemMenuBinding
import com.example.chowzy.utils.toRupiahFormat

class MenuListItemViewHolder(
    private val binding: ItemMenuBinding,
    private val itemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root),
    ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(it.imgUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.price.toRupiahFormat()

            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }
}