package com.mdg.notimematch.screens.closet

import android.R
import android.graphics.Bitmap
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdg.notimematch.localdb.room.entity.Garment
import com.mdg.notimematch.model.GarmentType
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun Closet(
    garments: List<Garment>,
    openCamera: () -> Unit,
    goToDetails: (garmentId: Int) -> Unit,
    getBitmapFromUriString: (uriString: String) -> Bitmap
) {
    // TODO: if the category is empty, show only a block with a "+" icon in the center
    Categories(
        garments = garments,
        openCamera = openCamera,
        goToDetails = goToDetails,
        getBitmapFromUriString = getBitmapFromUriString
    )
}

@Composable
private fun Categories(
    garments: List<Garment>,
    openCamera: () -> Unit,
    goToDetails: (garmentId: Int) -> Unit,
    getBitmapFromUriString: (uriString: String) -> Bitmap
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
                    val garmentTypeRes = GarmentType.getStringResourceForType(garmentType)
                    Text(
                        text = stringResource(id = garmentTypeRes),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryItems(
                        garmentTypeName = garmentTypeValue,
                        garments = garments,
                        openCamera = openCamera,
                        goToDetails = goToDetails,
                        getBitmapFromUriString = getBitmapFromUriString
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    garmentTypeName: String,
    garments: List<Garment>,
    openCamera: () -> Unit,
    goToDetails: (garmentId: Int) -> Unit,
    getBitmapFromUriString: (uriString: String) -> Bitmap
){
    LazyHorizontalGrid(
        modifier = Modifier
            .height(200.dp),
        rows = GridCells.Fixed(1)
    ){
        item {
            AddItemButton(
                categoryName = garmentTypeName,
                openCamera = openCamera
            )
        }
        // TODO: this should be a get garment per type
        garments.forEachIndexed { position, garment ->
            if(garment.type.value == garmentTypeName){
                item("garment$garmentTypeName$position"){
                    CategoryItem(
                        garment = garment,
                        goToDetails = goToDetails,
                        getBitmapFromUriString = getBitmapFromUriString
                    )
                }
            }
        }
    }
}

@Composable
fun AddItemButton(
    categoryName: String,
    openCamera: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = openCamera) {
            Text(text = "Add $categoryName")
        }
    }
}

@Composable
fun CategoryItem(
    garment: Garment,
    goToDetails: (garmentId: Int) -> Unit,
    getBitmapFromUriString: (uriString: String) -> Bitmap
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
        val bitmap = BitmapPainter(
            image = getBitmapFromUriString(
                garment.photoUriString
            ).asImageBitmap()
        )
        Image(
            painter = bitmap,
            contentDescription = null
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
                garments = ArrayList(),
                openCamera = {},
                goToDetails = {},
                getBitmapFromUriString = {
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.alert_dark_frame
                    )
                }
            )
        }
    }
}