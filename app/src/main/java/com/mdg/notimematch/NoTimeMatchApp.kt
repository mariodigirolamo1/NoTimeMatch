package com.mdg.notimematch

import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdg.notimematch.camera.Camera
import com.mdg.notimematch.camera.CameraViewModel
import com.mdg.notimematch.closet.Closet
import com.mdg.notimematch.closet.ClosetViewModel
import com.mdg.notimematch.home.Home
import com.mdg.notimematch.navigation.Routes
import java.io.File

@Composable
fun NoTimeMatchApp(
    navController: NavHostController = rememberNavController(),
    closetViewModel: ClosetViewModel,
    cameraViewModel: CameraViewModel,
    outputDirectory: File
) {
    NavHost(navController = navController, startDestination = Routes.HOME.value){
        composable(Routes.HOME.value){
            Home { route ->
                navController.navigate(route = route)
            }
        }
        composable(Routes.CLOSET.value){
            Closet(
                getAllGarments = { closetViewModel.getAllGarments() },
                openCamera = { navController.navigate(route = Routes.CAMERA.value) }
            )
        }
        composable(Routes.CAMERA.value){
            // TODO: refer to this https://www.kiloloco.com/articles/015-camera-jetpack-compose/ 
            Camera(
                takePhoto = { imageCapture: ImageCapture ->
                    cameraViewModel.takePhoto({},{},imageCapture,outputDirectory)
                }
            )
        }
    }
}