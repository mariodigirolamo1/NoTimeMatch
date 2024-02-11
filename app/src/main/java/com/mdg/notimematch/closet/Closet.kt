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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdg.notimematch.localdb.room.entity.Garment
import com.mdg.notimematch.model.GarmentType
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun Closet(
    getAllGarments: () -> List<Garment>
) {
    // TODO: if the category is empty, show only a block with a "+" icon in the center
    Categories(getAllGarments = getAllGarments)
}

@Composable
private fun Categories(
    getAllGarments: () -> List<Garment>
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
                        getAllGarments = getAllGarments
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    garmentTypeName: String,
    getAllGarments: () -> List<Garment>
){
    LazyHorizontalGrid(
        modifier = Modifier
            .height(200.dp),
        rows = GridCells.Fixed(1)
    ){
        item {
            AddItemButton(categoryName = garmentTypeName)
        }
        // TODO: add a function to retrieve garments by type, get all does not fit
        //  we could also choose to return a map with type as key and list as value
        //  so we end up doing just one function call, it is just a wrapper but it's fine
        //  for the low number fo fetches we'll need to do
    }
}

@Composable
fun AddItemButton(categoryName: String) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add $categoryName")
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
            Closet(getAllGarments = {ArrayList()})
        }
    }
}