package com.mdg.notimematch.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mdg.notimematch.R
import com.mdg.notimematch.navigation.Routes
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme

@Composable
fun Home(
    navigateToDestination: (route: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navigateToDestination(Routes.CLOSET.value)
        }){
            Text(stringResource(R.string.closet_navigation_button_text))
        }
        Button({}){
            Text(stringResource(R.string.matches_navigation_button_text))
        }
        Button({}){
            Text(stringResource(R.string.re_match_navigation_button_text))
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    // should I include theme and surface in home or not?
    NoTimeMatchTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Home {}
        }
    }
}