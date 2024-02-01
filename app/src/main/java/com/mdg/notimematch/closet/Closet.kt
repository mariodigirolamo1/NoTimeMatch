package com.mdg.notimematch.closet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun Closet() {
    // TODO: add vertical list of categories
    // TODO: in each category add a label and an horizontal scrollable gridview
    // TODO: in each block of the griview add placeholder images
    // TODO: if the category is empty, show only a block with a "+" icon in the center
    Categories()
}

@Composable
private fun Categories(){
    LazyColumn{
        repeat(5){categoryNum ->
            item(key = "category$categoryNum"){
                LazyHorizontalGrid(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(all = 20.dp),
                    rows = GridCells.Fixed(1)
                ){
                    repeat(10){categoryItemNum ->
                        val categoryItemKey = "category${categoryNum}item$categoryItemNum"
                        item(key = categoryItemKey) {
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                                    .padding(end = 10.dp)
                                    .background(MaterialTheme.colorScheme.onBackground),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = categoryItemKey,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }
            }
        }
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