package com.mdg.notimematch.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdg.notimematch.R
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun ConfirmPhoto(
    getBitmapFromUri: () -> Bitmap,
    savePhoto: () -> Unit,
    retakePhoto: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            bitmap = getBitmapFromUri().asImageBitmap(),
            contentDescription =
            stringResource(R.string.garment_photo_to_confirm_content_description),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ){
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .padding(8.dp),
                onClick = retakePhoto
            ){
                Text(text = stringResource(R.string.reject_photo_button_text))
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .padding(8.dp),
                onClick = savePhoto
            ){
                Text(text = stringResource(R.string.save_photo_button_text))
            }
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmPhotoPreview() {
    NoTimeMatchTheme {
        val context = LocalContext.current
        ConfirmPhoto(
            getBitmapFromUri = {
                BitmapFactory.decodeResource(
                    context.resources,
                    android.R.drawable.alert_dark_frame
                )
            },
            savePhoto = {},
            retakePhoto = {}
        )
    }
}