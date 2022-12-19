package com.mfk.roomexample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@Entity(tableName = "Favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    @ColumnInfo(name = "userUUID") var userUUID: String,
    @ColumnInfo(name = "productId") var productId: Int,
    @ColumnInfo(name = "createdAt") var createdAt: String
)
