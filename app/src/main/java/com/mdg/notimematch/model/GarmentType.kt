package com.mdg.notimematch.model

import com.mdg.notimematch.R

enum class GarmentType(val value: String) {
    SHOES(value = "SHOES"),
    PANTS(value = "PANTS"),
    SHIRT(value = "SHIRT"),
    JACKET(value = "JACKET");

    companion object {
        fun getStringResourceForType(type: GarmentType): Int = when(type){
            SHOES -> R.string.garment_type_shoes
            PANTS -> R.string.garment_type_pants
            SHIRT -> R.string.garment_type_shirt
            JACKET -> R.string.garment_type_jacket
        }
    }
}