package com.mdg.notimematch.screens.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdg.notimematch.R
import com.mdg.notimematch.model.GarmentType
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun ConfirmPhoto(
    getBitmapFromUri: () -> Bitmap,
    saveGarment: () -> Unit,
    retakePhoto: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        // TODO: use coil also there
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            bitmap = getBitmapFromUri().asImageBitmap(),
            contentDescription =
            stringResource(R.string.garment_photo_to_confirm_content_description),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Palette()

        Spacer(modifier = Modifier.height(32.dp))

        CallToActions(
            retakePhoto = retakePhoto,
            saveGarment = saveGarment
        )

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun Palette() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        ColorItem(color = Color.Red)
        ColorItem(color = Color.Green)
        ColorItem(color = Color.Gray)
        ColorItem(color = Color.Black)
    }
}

@Composable
fun ColorItem(
    color: Color
) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .size(50.dp)
            .clip(RoundedCornerShape(40.dp))
            .clickable {},
        color = color
    ){}
}

@Composable
fun CallToActions(
    retakePhoto: () -> Unit,
    saveGarment: () -> Unit
) {
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
            // TODO: ask user for correct type
            onClick = saveGarment
        ){
            Text(text = stringResource(R.string.save_photo_button_text))
        }
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
            saveGarment = {},
            retakePhoto = {}
        )
    }
}