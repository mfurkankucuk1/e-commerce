package com.mfk.roomexample.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mfk.roomexample.data.model.User

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long
}