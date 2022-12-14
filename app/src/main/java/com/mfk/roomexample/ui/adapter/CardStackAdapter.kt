package com.mfk.roomexample.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.databinding.ItemRowCardBinding
import com.mfk.roomexample.utils.CurrencyHelper

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
class CardStackAdapter : RecyclerView.Adapter<CardStackAdapter.DiscoverViewHolder>() {

    inner class DiscoverViewHolder(val binding: ItemRowCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    var list = ArrayList<Product>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverViewHolder {
        return DiscoverViewHolder(
            ItemRowCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: DiscoverViewHolder,
        position: Int
    ) {
        val currentItem = list[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(currentItem.image)
                .fitCenter()
                .into(imgProduct)
            tvProductName.text = currentItem.title
            currentItem.price?.let {
                tvProductPrice.text = CurrencyHelper.getCurrency(it)
            }
        }
    }

    override fun getItemCount() = list.size
}