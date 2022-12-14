package com.mfk.roomexample.utils

import androidx.recyclerview.widget.DiffUtil
import com.mfk.roomexample.data.model.Product


/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
class CardStackCallback(oldList: List<Product>, newList: List<Product>) :
    DiffUtil.Callback() {
    private val oldList: List<Product>
    private val newList: List<Product>
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].image === newList[newItemPosition].image
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    init {
        this.oldList = oldList
        this.newList = newList
    }
}