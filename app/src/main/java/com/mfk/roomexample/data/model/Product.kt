package com.mfk.roomexample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Entity(tableName = "Products")
data class Product(
    @PrimaryKey @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("image") var image: String? = null,
    @ColumnInfo(name = "createdAt") var createdAt: String? = null,
    @ColumnInfo(name = "count") var count: Int? = null,
    @ColumnInfo(name = "isAddedFavorite") var isAddedFavorite: Boolean? = null,
    @ColumnInfo(name = "isAddedCart") var isAddedCart: Boolean? = null
)
