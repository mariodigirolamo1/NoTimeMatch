package com.mdg.notimematch.confirmphoto

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConfirmPhoto(
    photoUri: Uri
) {
    // TODO: take photo uri, show it on screen
    // TODO: show accept, retake, exit buttons
    Text(text = "photo uri is $photoUri")
}