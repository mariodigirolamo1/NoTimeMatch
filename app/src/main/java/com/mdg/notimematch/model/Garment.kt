package com.mdg.notimematch.model

sealed class Garment(
    val hexColor: String,
    val type: String
){
    sealed class Shoes(hexColor: String): Garment(hexColor = hexColor, type = GarmentTypes.SHOES.name)
    sealed class Pants(hexColor: String): Garment(hexColor = hexColor, type = GarmentTypes.PANTS.name)
    sealed class Shirt(hexColor: String): Garment(hexColor = hexColor, type = GarmentTypes.SHIRT.name)
    sealed class Jacket(hexColor: String): Garment(hexColor = hexColor, type = GarmentTypes.JACKET.name)
}