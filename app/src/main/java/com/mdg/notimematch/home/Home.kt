package com.mdg.notimematch.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mdg.notimematch.R

@Composable
fun Home() {
    Column {
        Button({}){
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