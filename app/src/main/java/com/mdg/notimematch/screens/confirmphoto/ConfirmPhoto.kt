package com.mdg.notimematch.screens.confirmphoto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.mdg.notimematch.R
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme
import org.jetbrains.annotations.Async

@Composable
fun ConfirmPhoto(
    getViewState: () -> ConfirmPhotoViewState,
    getPhotoUri: () -> String,
    saveGarment: () -> Unit,
    retakePhoto: () -> Unit,
    onColorSelect: (color: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(data = getPhotoUri())
                .crossfade(enable = true)
                .crossfade(500)
                .scale(Scale.FILL)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(32.dp))

        Palette(
            getViewState = getViewState,
            onColorSelect = onColorSelect
        )

        Spacer(modifier = Modifier.height(32.dp))

        CallToActions(
            retakePhoto = retakePhoto,
            saveGarment = saveGarment
        )

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun Palette(
    getViewState: () -> ConfirmPhotoViewState,
    onColorSelect: (color: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        val viewState = getViewState()
        if(viewState is ConfirmPhotoViewState.Loading){
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
            )
        }else{
            viewState as ConfirmPhotoViewState.Ready

            with(viewState.palette){
                ColorItem(
                    color = getDarkMutedColor(0xFFFFFF),
                    selectedColor = viewState.selectedColor,
                    onColorSelect = onColorSelect
                )
                ColorItem(
                    color = getDominantColor(0xFFFFFF),
                    selectedColor = viewState.selectedColor,
                    onColorSelect = onColorSelect
                )
                ColorItem(
                    color = getMutedColor(0xFFFFFF),
                    selectedColor = viewState.selectedColor,
                    onColorSelect = onColorSelect
                )
            }
        }
    }
}

@Composable
fun ColorItem(
    color: Int,
    selectedColor: Int,
    onColorSelect: (color: Int) -> Unit
) {
    val isSelected = selectedColor == color

    Box{
        Surface(
            modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(40.dp))
                .clickable { onColorSelect(color) },
            color = Color(color)
        ){}
        if(isSelected){
            Icon(
                painterResource(id = android.R.drawable.presence_invisible),
                contentDescription = null
            )
        }
    }

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
            getViewState = { ConfirmPhotoViewState.Loading },
            getPhotoUri = { "" },
            saveGarment = {},
            retakePhoto = {},
            onColorSelect = {}
        )
    }
}