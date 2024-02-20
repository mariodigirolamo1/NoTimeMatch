package com.mdg.notimematch

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mdg.notimematch.camera.Camera
import com.mdg.notimematch.camera.CameraViewModel
import com.mdg.notimematch.closet.Closet
import com.mdg.notimematch.closet.ClosetViewModel
import com.mdg.notimematch.confirmphoto.ConfirmPhoto
import com.mdg.notimematch.confirmphoto.ConfirmPhotoViewModel
import com.mdg.notimematch.home.Home
import com.mdg.notimematch.localdb.room.entity.Garment
import com.mdg.notimematch.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.ExecutorService

@Composable
fun NoTimeMatchApp(
    navController: NavHostController = rememberNavController(),
    closetViewModel: ClosetViewModel,
    cameraViewModel: CameraViewModel,
    confirmPhotoViewModel: ConfirmPhotoViewModel,
    outputDirectory: File,
    cameraExecutor: ExecutorService
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
                    cameraViewModel.takePhoto(
                        onImageCaptured = { uri, coroutineScope ->
                            val encodedUri = Uri.encode(uri.toString())
                            coroutineScope.launch{
                                withContext(Dispatchers.Main){
                                    println("should navigate")
                                    navController.navigate("${Routes.CONFIRM_PHOTO.value}/$encodedUri")
                                }
                            }
                        },
                        onError = {
                            println("Error in taking photo")
                            it.printStackTrace()
                        },
                        imageCapture = imageCapture,
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor
                    )
                }
            )
        }
        composable(
            route = "${Routes.CONFIRM_PHOTO.value}/{photoUriString}",
            arguments = listOf(navArgument("photoUriString") { type = NavType.StringType })
        ){ backStackEntry ->
            val encodedPhotoUriString = backStackEntry.arguments?.getString("photoUriString")
            val photoUriString = Uri.decode(encodedPhotoUriString)
            ConfirmPhoto(
                getBitmapFromUri = {
                    confirmPhotoViewModel.getBitmapFromUri(Uri.parse(photoUriString))
                },
                saveGarment = { type ->
                    val garment = Garment(
                        type = type,
                        // TODO: this will need to be extracted from code
                        hexColor = "#FFFFFF",
                        photoUriString = photoUriString
                    )
                    confirmPhotoViewModel.saveGarmentToDB(garment = garment)

                    navController.popBackStack()
                    navController.navigate(Routes.CLOSET.value)
                },
                retakePhoto = {
                    navController.popBackStack()
                }
            )
        }
    }
}