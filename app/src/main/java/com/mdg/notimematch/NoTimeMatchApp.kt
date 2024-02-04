package com.mdg.notimematch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdg.notimematch.closet.Closet
import com.mdg.notimematch.home.Home
import com.mdg.notimematch.navigation.Routes

@Composable
fun NoTimeMatchApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.HOME.value){
        composable(Routes.HOME.value){
            Home { route ->
                navController.navigate(route = route)
            }
        }
        composable(Routes.CLOSET.value){
            Closet()
        }
    }
}