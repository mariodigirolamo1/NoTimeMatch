package com.mdg.notimematch.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPhotoViewModel @Inject constructor(
    @RoomDB
    val localDB: LocalDB
) : ViewModel() {
    fun getBitmapFromUri(photoUri: Uri): Bitmap {
        return BitmapFactory.decodeFile(photoUri.toFile().path)
    }

    fun saveGarmentToDB(
        garment: Garment
    ){
        viewModelScope.launch(Dispatchers.IO) {
            localDB.addGarment(garment)
        }
    }
}