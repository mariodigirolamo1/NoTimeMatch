package com.mdg.notimematch.screens.confirmphoto

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.mdg.notimematch.localdb.LocalDB
import com.mdg.notimematch.localdb.di.RoomDB
import com.mdg.notimematch.localdb.room.entity.Garment
import com.mdg.notimematch.model.GarmentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ConfirmPhotoViewModel @Inject constructor(
    @RoomDB
    val localDB: LocalDB
) : ViewModel() {
    private var _viewState = MutableStateFlow<ConfirmPhotoViewState>(ConfirmPhotoViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private var _image = MutableStateFlow<Bitmap?>(null)
    val image = _image.asStateFlow()

    fun updateImage(bitmap: Bitmap){
        _image.update { bitmap }
    }

    fun saveGarmentToDB(
        garmentType: GarmentType,
        outputDirectory: File
    ){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _image.value!!
            }.onSuccess { bitmap ->
                val imageUriString = savePhoto(
                    outputDirectory = outputDirectory,
                    bitmap = bitmap
                )
                val garment = Garment(
                    type = garmentType,
                    color = (viewState.value as ConfirmPhotoViewState.Ready).selectedColor,
                    photoUriString = imageUriString
                )
                localDB.addGarment(garment)
            }.onFailure {
                // TODO: handle this
            }
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
    fun getPalette(bitmap: Bitmap){
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

    private fun savePhoto(
        outputDirectory: File,
        bitmap: Bitmap
    ): String {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                /* pattern = */ FILENAME_FORMAT,
                /* locale = */ Locale.getDefault()
            ).format(System.currentTimeMillis()) + PHOTO_FILE_EXTENSION
        )
        photoFile.outputStream().use {
            bitmap.compress(
                /* format = */ Bitmap.CompressFormat.JPEG,
                /* quality = */ 40,
                /* stream = */ it)
        }
        return Uri.fromFile(photoFile).toString()
    }

    private companion object {
        const val PHOTO_FILE_EXTENSION = ".jpg"
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}