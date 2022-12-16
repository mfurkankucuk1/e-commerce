package com.mfk.roomexample.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.databinding.ItemRowProductBinding
import com.mfk.roomexample.utils.CurrencyHelper.getCurrency

/**
 * Created by M.Furkan KÜÇÜK on 9.12.2022
 **/
class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(val binding: ItemRowProductBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ItemRowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var list = ArrayList<Product>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            tvProductName.text = currentItem.title
            currentItem.price?.let {
                tvProductPrice.text = getCurrency(it)
            }
            Glide.with(holder.itemView.context)
                .load(currentItem.image)
                .fitCenter()
                .into(imgProduct)

            root.setOnClickListener {
                onItemClickListener?.let {
                    it(currentItem)
                }
            }
        }
    }

    override fun getItemCount() = list.size

    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener:(Product)->Unit){
        onItemClickListener = listener
    }
}