package com.mdg.notimematch.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmPhotoViewModel @Inject constructor() : ViewModel() {
    fun getBitmapFromUri(photoUri: Uri): Bitmap {
        return BitmapFactory.decodeFile(photoUri.toFile().path)
    }
}