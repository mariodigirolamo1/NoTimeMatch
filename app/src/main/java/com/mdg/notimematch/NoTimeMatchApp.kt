package com.mdg.notimematch

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mdg.notimematch.screens.camera.Camera
import com.mdg.notimematch.screens.camera.CameraViewModel
import com.mdg.notimematch.screens.closet.Closet
import com.mdg.notimematch.screens.closet.ClosetViewModel
import com.mdg.notimematch.screens.confirmphoto.ConfirmPhoto
import com.mdg.notimematch.screens.confirmphoto.ConfirmPhotoViewModel
import com.mdg.notimematch.screens.garmentdetails.GarmentDetails
import com.mdg.notimematch.screens.home.Home
import com.mdg.notimematch.localdb.room.entity.Garment
import com.mdg.notimematch.navigation.Routes
import com.mdg.notimematch.screens.garmentdetails.GarmentDetailsViewModel
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
    garmentDetailsViewModel: GarmentDetailsViewModel,
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
            closetViewModel.fetchGarments()
            val context = LocalContext.current
            Closet(
                garments = closetViewModel.garments.collectAsState().value,
                openCamera = {
                    navController.navigate(route = Routes.CAMERA.value)
                },
                goToDetails = { garmentId: Int ->
                    navController.navigate("${Routes.GARMENT_DETAILS.value}/$garmentId")
                },
                getBitmapFromUriString = {uriString ->
                    closetViewModel.getBitmapFromUriString(
                        context = context,
                        uriString = uriString
                    )
                }
            )
        }
        composable(
            route = "${Routes.GARMENT_DETAILS.value}/{garmentId}",
            arguments = listOf(navArgument("garmentId") { type = NavType.IntType })
        ){backStackEntry ->
            val garmentId = backStackEntry.arguments?.getInt("garmentId")
            if (garmentId != null) {
                garmentDetailsViewModel.fetchGarment(garmentId = garmentId)
                val garment = garmentDetailsViewModel.garment.collectAsState().value
                if (garment != null) {
                    GarmentDetails(
                        getBitmapFromUriString = {
                            garmentDetailsViewModel.getBitmapFromUri(
                                photoUri = Uri.parse(garment.photoUriString)
                            )
                        }
                    )
                }else{
                    // TODO: handle null garment
                }
            }else{
                // TODO: handle null garment id
            }
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

                    navController.popBackStack(Routes.HOME.value,false)
                    navController.navigate(Routes.CLOSET.value)
                },
                retakePhoto = {
                    navController.popBackStack()
                }
            )
        }
    }
}