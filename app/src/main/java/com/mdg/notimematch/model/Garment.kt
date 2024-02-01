package com.mdg.notimematch.model

sealed class Garment(
    val hexColor: String
){
    sealed class Shoes(hexColor: String): Garment(hexColor)
    sealed class Pants(hexColor: String): Garment(hexColor)
    sealed class Shirt(hexColor: String): Garment(hexColor)
    sealed class Jacket(hexColor: String): Garment(hexColor)

    companion object {
        fun getNumberOfCategories(): Int {
            val sealedClass = Garment::class
            return sealedClass.sealedSubclasses.size
        }
    }

}