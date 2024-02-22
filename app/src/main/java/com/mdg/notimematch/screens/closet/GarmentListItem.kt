package com.mdg.notimematch.screens.closet

import android.graphics.Bitmap
import com.mdg.notimematch.localdb.room.entity.Garment

data class GarmentListItem(
    val garment: Garment,
    var bitmap: Bitmap? = null
)