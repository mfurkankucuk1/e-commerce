package com.mfk.roomexample.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.databinding.ItemRowCartBinding
import com.mfk.roomexample.utils.CurrencyHelper.getCurrency

/**
 * Created by M.Furkan KÜÇÜK on 20.12.2022
 **/
class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    inner class CartViewHolder(val binding: ItemRowCartBinding) :
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
    ): CartAdapter.CartViewHolder {

        return CartViewHolder(
            ItemRowCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CartAdapter.CartViewHolder,
        position: Int
    ) {
        val currentItem = list[position]
        holder.binding.apply {
            tvProductName.text = currentItem.title
            currentItem.price?.let { prc ->
                tvProductPrice.text = getCurrency(prc)
            }
            Glide.with(holder.itemView.context)
                .load(currentItem.image)
                .fitCenter()
                .into(imgProduct)

        }
    }

    override fun getItemCount() = list.size
}