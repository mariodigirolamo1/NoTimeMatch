package com.mdg.notimematch.closet

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdg.notimematch.model.GarmentTypes
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun Closet() {
    // TODO: if the category is empty, show only a block with a "+" icon in the center
    Categories()
}

@Composable
private fun Categories(){
    LazyColumn{
        GarmentTypes.values().forEach { garmentType ->
            val garmentTypeValue = garmentType.value

            item(key = garmentTypeValue){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp),
                ) {
                    Text(
                        text = garmentTypeValue,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryItems(garmentTypeName = garmentTypeValue)
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    garmentTypeName: String
){
    LazyHorizontalGrid(
        modifier = Modifier
            .height(200.dp),
        rows = GridCells.Fixed(1)
    ){
        // TODO: this is a mock number, those need to be retrieved from a local database
        repeat(10){categoryItemNum ->
            item(key = "${garmentTypeName}_item_$categoryItemNum") {
                CategoryItem(categoryItemNum = categoryItemNum)
            }
        }
    }
}

@Composable
fun CategoryItem(categoryItemNum: Int) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = categoryItemNum.toString(),
            color = MaterialTheme.colorScheme.background
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
            Closet()
        }
    }
}