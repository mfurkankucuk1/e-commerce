package com.mfk.roomexample.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mfk.roomexample.data.model.FavoriteModel
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.databinding.ItemRowProductBinding
import com.mfk.roomexample.utils.CurrencyHelper.getCurrency

/**
 * Created by M.Furkan KÜÇÜK on 9.12.2022
 **/
class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: ItemRowProductBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemRowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var list = ArrayList<FavoriteModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
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

    private var onItemClickListener: ((FavoriteModel) -> Unit)? = null

    fun setOnItemClickListener(listener:(FavoriteModel)->Unit){
        onItemClickListener = listener
    }
}