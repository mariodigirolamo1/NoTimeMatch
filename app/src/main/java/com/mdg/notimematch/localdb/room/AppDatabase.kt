package com.mdg.notimematch.localdb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mdg.notimematch.localdb.room.dao.GarmentDao
import com.mdg.notimematch.localdb.room.entity.Garment

@Database(entities = [Garment::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun garmentDao(): GarmentDao
}