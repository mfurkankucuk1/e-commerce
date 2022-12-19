package com.mfk.roomexample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@Entity(tableName = "Cart")
data class Cart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userUUID") var userUUID: String?=null,
    @ColumnInfo(name = "productId") var productId: Int,
    @ColumnInfo(name = "quantity") var quantity: Int,
    @ColumnInfo(name = "createdAt") var createdAt: String?=null,
)