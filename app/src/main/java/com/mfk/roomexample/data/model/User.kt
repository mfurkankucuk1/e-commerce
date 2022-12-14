package com.mfk.roomexample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
@Entity(tableName = "User")
data class User(
    @PrimaryKey @SerializedName("id") var id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "userName") var userName: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "surname") var surname: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "createdAt") var createdAt: String? = null,
)