package com.mfk.roomexample.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfk.roomexample.data.model.Category
import com.mfk.roomexample.databinding.ItemRowFilterBinding

/**
 * Created by M.Furkan KÜÇÜK on 9.12.2022
 **/
class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(val binding: ItemRowFilterBinding) :
        RecyclerView.ViewHolder(binding.root)

    var list = ArrayList<Category>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        return FilterViewHolder(
            ItemRowFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        position: Int
    ) {
        val currentItem = list[position]
        holder.binding.apply {
            tvFilterName.text = currentItem.category?.uppercase()
            tvFilterCount.text = "(${currentItem.count})"
            root.setOnClickListener {
                onClickItemListener?.let {
                    it(currentItem)
                }
            }
        }

    }

    override fun getItemCount() = list.size

    private var onClickItemListener: ((Category) -> Unit)? = null
    fun setOnClickItemListener(listener: (Category) -> Unit) {
        onClickItemListener = listener
    }
}