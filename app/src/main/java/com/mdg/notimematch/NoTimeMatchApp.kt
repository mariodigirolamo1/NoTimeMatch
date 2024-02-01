package com.mdg.notimematch

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdg.notimematch.home.Home
import com.mdg.notimematch.navigation.Routes

@Composable
fun NoTimeMatchApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME.value){
        composable(Routes.HOME.value){
            Home()
        }
    }
}