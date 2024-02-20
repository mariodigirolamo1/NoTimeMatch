package com.mdg.notimematch.screens.garmentdetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mdg.notimematch.localdb.room.entity.Garment

@Composable
fun GarmentDetails(
    garment: Garment
) {
    Text("garment details for id ${garment.uid}")
}