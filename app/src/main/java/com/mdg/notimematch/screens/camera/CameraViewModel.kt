package com.mdg.notimematch.screens.camera

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CameraViewModel @Inject constructor(): ViewModel() {
    fun takePhoto(
        //onImageCaptured: (Uri, CoroutineScope) -> Unit,
        onImageCaptured: (Bitmap, CoroutineScope) -> Unit,
        onError: (ImageCaptureException) -> Unit,
        imageCapture: ImageCapture,
        executor: ExecutorService
    ){
        viewModelScope.launch(Dispatchers.IO) {
            imageCapture.takePicture(executor, object: ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val rotatedBitmap = rotateBitmap(
                        source = image.toBitmap(),
                        degrees = image.imageInfo.rotationDegrees.toFloat()
                    )
                    onImageCaptured(rotatedBitmap, viewModelScope)
                    /*
                    val savedUri = Uri.fromFile(photoFile)
                    onImageCaptured(savedUri, viewModelScope)
                     */
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            })
        }
    }

    fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }
}