package com.mdg.notimematch.screens.closet

import android.R
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.mdg.notimematch.localdb.room.entity.Garment
import com.mdg.notimematch.model.GarmentType
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun Closet(
    context: Context,
    getViewState: () -> ClosetViewState,
    openCamera: (garmentTypeValue: String) -> Unit,
    goToDetails: (garmentId: Int) -> Unit
) {
    Categories(
        context = context,
        getViewState = getViewState,
        openCamera = openCamera,
        goToDetails = goToDetails
    )
}

@Composable
private fun Categories(
    context: Context,
    getViewState: () -> ClosetViewState,
    openCamera: (garmentTypeValue: String) -> Unit,
    goToDetails: (garmentId: Int) -> Unit
){
    LazyColumn{
        GarmentType.entries.forEach { garmentType ->
            val garmentTypeValue = garmentType.value

            item(key = garmentTypeValue){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp),
                ) {
                    Text(
                        text = stringResource(id = GarmentType.getStringResourceForType(garmentType)),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryItems(
                        garmentTypeName = garmentTypeValue,
                        getViewState = getViewState,
                        context = context,
                        openCamera = openCamera,
                        goToDetails = goToDetails
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    garmentTypeName: String,
    getViewState: () -> ClosetViewState,
    context: Context,
    openCamera: (garmentTypeValue: String) -> Unit,
    goToDetails: (garmentId: Int) -> Unit
){
    LazyHorizontalGrid(
        modifier = Modifier
            .height(200.dp),
        rows = GridCells.Fixed(1)
    ){
        item(key = "addButton$garmentTypeName") {
            AddItemButton(
                categoryName = garmentTypeName,
                openCamera = openCamera
            )
        }
        val viewState = getViewState()
        if(viewState == ClosetViewState.Loading){
            item(key = "loadingItem$garmentTypeName") {
                LoadingItem()
            }
        }else{
            viewState as ClosetViewState.Ready
            viewState.garments.forEachIndexed { position, garment ->
                if(garment.type.value == garmentTypeName){
                    item("garment$garmentTypeName$position"){
                        CategoryItem(
                            garment = garment,
                            context = context,
                            goToDetails = goToDetails
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddItemButton(
    categoryName: String,
    openCamera: (garmentTypeValue: String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { openCamera(categoryName) }) {
            Text(text = "Add $categoryName")
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun CategoryItem(
    garment: Garment,
    context: Context,
    goToDetails: (garmentId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.onBackground)
            .clickable { garment.uid?.let { goToDetails(it) } },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(data = garment.photoUriString)
                .crossfade(enable = true)
                .crossfade(500)
                .scale(Scale.FILL)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ClosetPreview() {
    NoTimeMatchTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            Closet(
                context = context,
                openCamera = {},
                getViewState = { ClosetViewState.Ready(ArrayList()) },
                goToDetails = {}
            )
        }
    }
}