package com.mdg.notimematch.confirmphoto

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun ConfirmPhoto(
    getBitmapFromUri: () -> Bitmap
) {
    // TODO: take photo uri, show it on screen
    // TODO: show accept, retake, exit buttons
    // TODO: check rotations errore and inconsistencies
    Image(
        bitmap = getBitmapFromUri().asImageBitmap(),
        contentDescription = "",
        modifier = Modifier.fillMaxSize()
    )
}