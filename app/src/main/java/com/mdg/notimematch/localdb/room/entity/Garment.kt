package com.mdg.notimematch.localdb.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mdg.notimematch.model.GarmentType

@Entity
data class Garment(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "type") val type: GarmentType,
    @ColumnInfo(name = "hexColor") val hexColor: String,
    @ColumnInfo(name = "photoUriString") val photoUriString: String
)