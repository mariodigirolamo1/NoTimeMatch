package com.mdg.notimematch.localdb.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mdg.notimematch.model.GarmentType

@Entity
data class Garment(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "type") val type: GarmentType,
    @ColumnInfo(name = "hexColor") val hexColor: String
)