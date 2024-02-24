package com.mdg.notimematch.screens.confirmphoto

import android.graphics.Bitmap
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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

@Composable
fun ConfirmPhoto(
    getViewState: () -> ConfirmPhotoViewState,
    getBitmap: () -> Bitmap?,
    saveGarment: () -> Unit,
    retakePhoto: () -> Unit,
    onColorSelect: (color: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        // TODO: needs just image?
        AsyncImage(
            modifier = Modifier.weight(5f),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(data = getBitmap())
                .crossfade(enable = true)
                .crossfade(500)
                .scale(Scale.FIT)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.weight(1f))

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
    LazyHorizontalGrid(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        rows = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        val viewState = getViewState()
        if(viewState is ConfirmPhotoViewState.Loading){
            item{
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(40.dp))
                )
            }
        }else{
            viewState as ConfirmPhotoViewState.Ready

            with(viewState.palette){
                val colors = HashSet<Int>()
                colors.add(0xFFFFFF)
                colors.add(0x000000)
                colors.add(getDominantColor(0x000000))
                colors.add(getMutedColor(0x000000))
                colors.add(getVibrantColor(0x000000))
                colors.add(getDarkMutedColor(0x000000))
                colors.add(getDarkVibrantColor(0x000000))
                colors.add(getLightMutedColor(0x000000))
                colors.add(getLightVibrantColor(0x000000))

                colors.forEach{ color ->
                    item{
                        ColorItem(
                            color = color,
                            selectedColor = viewState.selectedColor,
                            onColorSelect = onColorSelect
                        )
                    }
                }
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
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(40.dp)
                )
                .size(50.dp)
                .clip(RoundedCornerShape(30.dp))
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
        ConfirmPhoto(
            getViewState = { ConfirmPhotoViewState.Loading },
            getBitmap = { null },
            saveGarment = {},
            retakePhoto = {},
            onColorSelect = {}
        )
    }
}