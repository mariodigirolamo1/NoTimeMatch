package com.mdg.notimematch.localdb.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdg.notimematch.R
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.room.dao.GarmentDao
import com.mdg.notimematch.localdb.room.entity.Garment

class RoomDBWrapper: LocalDB {
    private lateinit var db: AppDatabase
    private lateinit var garmentDao: GarmentDao
    override fun setup(applicationContext: Context) {
        db = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = applicationContext.getString(R.string.database_name)
        ).build()

        garmentDao = db.garmentDao()
    }

    override fun getAllGarments(): List<Garment> {
        return garmentDao.getAll()
    }

    override fun addGarment(garment: Garment) {
        garmentDao.insertAll(garment)
    }

    override fun deleteGarment(garment: Garment) {
        garmentDao.delete(garment)
    }
}