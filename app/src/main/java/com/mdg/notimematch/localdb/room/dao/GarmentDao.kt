package com.mdg.notimematch.localdb.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mdg.notimematch.localdb.room.entity.Garment

@Dao
interface GarmentDao {
    @Query("SELECT * FROM garment")
    fun getAll(): List<Garment>

    @Insert
    fun insertAll(vararg garment: Garment)

    @Delete
    fun delete(garment: Garment)
}