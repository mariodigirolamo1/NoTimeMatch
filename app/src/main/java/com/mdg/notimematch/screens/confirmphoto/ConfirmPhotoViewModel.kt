package com.mdg.notimematch.screens.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
    private var _viewState = MutableStateFlow<ConfirmPhotoViewState>(ConfirmPhotoViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun saveGarmentToDB(
        garment: Garment
    ){
        viewModelScope.launch(Dispatchers.IO) {
            localDB.addGarment(garment)
        }
    }

    fun updateSelectedColor(color: Int){
        viewModelScope.launch(Dispatchers.IO){
            _viewState.update {
                it as ConfirmPhotoViewState.Ready
                ConfirmPhotoViewState.Ready(
                    palette = it.palette,
                    selectedColor = color
                )
            }
        }
    }

    // TODO: refere to this for palette:
    //  https://developer.android.com/develop/ui/views/graphics/palette-colors
    fun getPalette(photoUri: Uri){
        val bitmap = BitmapFactory.decodeFile(photoUri.toFile().path)
        viewModelScope.launch(Dispatchers.IO) {
            Palette.from(bitmap).generate { palette ->
                palette?.let{
                    _viewState.update {
                        ConfirmPhotoViewState.Ready(
                            palette = palette,
                            selectedColor = palette.getDominantColor(0xFFFFFF)
                        )
                    }
                }
            }
        }
    }
}