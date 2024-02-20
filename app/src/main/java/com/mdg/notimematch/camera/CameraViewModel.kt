package com.mdg.notimematch.camera

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
        onImageCaptured: (Uri, CoroutineScope) -> Unit,
        onError: (ImageCaptureException) -> Unit,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: ExecutorService
    ){
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + PHOTO_FILE_EXTENSION
        )
        //val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(executor, object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                // TODO: save after rotate
                Log.i(TAG, "onCaptureSuccess: image captured")
                val rotatedBitmap = rotateBitmap(
                    source = image.toBitmap(),
                    degrees = image.imageInfo.rotationDegrees.toFloat()
                )
                photoFile.outputStream().use { 
                    rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                }
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri, viewModelScope)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, "Take photo error:", exception)
                onError(exception)
            }
        })

        /*
        imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, "Take photo error:", exception)
                onError(exception)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println("image saved")
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri, viewModelScope)
            }
        })
         */
    }

    fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }

    private companion object {
        const val PHOTO_FILE_EXTENSION = ".jpg"
        const val TAG = "CameraViewModel"
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}