package com.mdg.notimematch.screens.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPhotoViewModel @Inject constructor(
    @RoomDB
    val localDB: LocalDB
) : ViewModel() {
    private var _palette = MutableStateFlow<ConfirmPhotoViewState>(ConfirmPhotoViewState.Loading)
    val palette = _palette.asStateFlow()

    // TODO: do this in coil
    fun getBitmapFromUri(photoUri: Uri): Bitmap {
        val bitmap = BitmapFactory.decodeFile(photoUri.toFile().path)
        getPalette(bitmap = bitmap)
        return bitmap
    }

    fun saveGarmentToDB(
        garment: Garment
    ){
        viewModelScope.launch(Dispatchers.IO) {
            localDB.addGarment(garment)
        }
    }

    private fun getPalette(bitmap: Bitmap){
        viewModelScope.launch(Dispatchers.IO) {
            Palette.from(bitmap).generate { palette ->
                palette?.let{
                    _palette.update {
                        ConfirmPhotoViewState.Ready(palette)
                    }
                }
            }
        }
    }
}