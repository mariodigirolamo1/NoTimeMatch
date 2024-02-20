package com.mdg.notimematch.localdb

import android.content.Context
import com.mdg.notimematch.localdb.room.entity.Garment

interface LocalDB {
    fun setup(applicationContext: Context)
    fun getAllGarments(): List<Garment>
    fun getGarmentById(uid: Int): Garment
    fun addGarment(garment: Garment)
    fun deleteGarment(garment: Garment)
}