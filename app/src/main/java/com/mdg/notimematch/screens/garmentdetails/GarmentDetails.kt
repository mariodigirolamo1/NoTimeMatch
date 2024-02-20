package com.mdg.notimematch.screens.garmentdetails

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun GarmentDetails(
    getBitmapFromUriString: () -> Bitmap,
    deleteGarment: () -> Unit
) {
    val photoBitmap = getBitmapFromUriString()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            bitmap = photoBitmap.asImageBitmap(),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(74.dp)
                    .padding(bottom = 32.dp),
                onClick = { deleteGarment() }
            ) {
                Text(text = "Delete garment")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GarmentDetailsPreview() {
    NoTimeMatchTheme {
        val context = LocalContext.current
        GarmentDetails {
            BitmapFactory.decodeResource(
                context.resources,
                android.R.drawable.alert_dark_frame
            )
        }
    }
}