package com.mdg.notimematch.screens.garmentdetails

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GarmentDetailsViewModel @Inject constructor(
    @RoomDB
    val localDB: LocalDB
) : ViewModel() {
    private var _garment = MutableStateFlow<Garment?>(null)
    val garment = _garment.asStateFlow()

    fun fetchGarment(garmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _garment.update {
                localDB.getGarmentById(uid = garmentId)
            }
        }
    }

    fun deleteGarment(
        navigateToCloset: () -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _garment.value?.let { garment -> localDB.deleteGarment(garment) }
            withContext(Dispatchers.Main){
                navigateToCloset()
            }
        }
    }

    fun getBitmapFromUri(photoUri: Uri): Bitmap {
        return BitmapFactory.decodeFile(photoUri.toFile().path)
    }
}