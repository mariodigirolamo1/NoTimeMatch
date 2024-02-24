package com.mdg.notimematch

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.mdg.notimematch.model.GarmentType
import com.mdg.notimematch.navigation.Routes
import com.mdg.notimematch.screens.confirmphoto.ConfirmPhotoViewState
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
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Routes.HOME.value) {
        composable(Routes.HOME.value) {
            Home { route ->
                navController.navigate(route = route)
            }
        }
        composable(Routes.CLOSET.value) {
            LaunchedEffect(Unit) {
                closetViewModel.fetchGarments()
            }
            val viewState = closetViewModel.viewState.collectAsState()
            Closet(
                context = context,
                openCamera = { garmentTypeValue ->
                    val route = StringBuilder(Routes.CAMERA.value)
                        .append("/")
                        .append(garmentTypeValue)

                    navController.navigate(route = route.toString())
                },
                getViewState = { viewState.value },
                goToDetails = { garmentId: Int ->
                    navController.navigate("${Routes.GARMENT_DETAILS.value}/$garmentId")
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
                        },
                        deleteGarment = {
                            garmentDetailsViewModel.deleteGarment {
                                navController.popBackStack(Routes.HOME.value,false)
                                navController.navigate(Routes.CLOSET.value)
                            }
                        },
                        getGarmentColor = { garment.color }
                    )
                }else{
                    // TODO: handle null garment
                }
            }else{
                // TODO: handle null garment id
            }
        }
        composable(
            route = "${Routes.CAMERA.value}/{garmentTypeValue}",
            arguments = listOf(
                navArgument("garmentTypeValue") { type = NavType.StringType }
            )
        ){backStackEntry ->
            // TODO: refer to this https://www.kiloloco.com/articles/015-camera-jetpack-compose/
            runCatching {
                backStackEntry.arguments!!.getString("garmentTypeValue")
            }.onSuccess { garmentTypeValue ->
                Camera(
                    takePhoto = { imageCapture: ImageCapture ->
                        cameraViewModel.takePhoto(
                            onImageCaptured = { uri, coroutineScope ->
                                val encodedUri = Uri.encode(uri.toString())
                                coroutineScope.launch{
                                    withContext(Dispatchers.Main){
                                        val route = StringBuilder(Routes.CONFIRM_PHOTO.value)
                                            .append("/")
                                            .append(encodedUri)
                                            .append("/")
                                            .append(garmentTypeValue)

                                        navController.navigate(route = route.toString())
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
            }.onFailure {
                // TODO: handle error
            }
        }
        composable(
            route = "${Routes.CONFIRM_PHOTO.value}/{photoUriString}/{garmentTypeValue}",
            arguments = listOf(
                navArgument("photoUriString") { type = NavType.StringType },
                navArgument("garmentTypeValue") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            runCatching {
                val encodedPhotoUriString = backStackEntry.arguments!!.getString("photoUriString")
                val garmentTypeValue = backStackEntry.arguments!!.getString("garmentTypeValue")
                val garmentType: GarmentType? = GarmentType.from(garmentTypeValue!!)
                val photoUriString = Uri.decode(encodedPhotoUriString)
                val viewState = confirmPhotoViewModel.viewState.collectAsState()
                LaunchedEffect(Unit) {
                    confirmPhotoViewModel.getPalette(Uri.parse(photoUriString))
                }
                ConfirmPhoto(
                    getViewState = { viewState.value },
                    getPhotoUri = { photoUriString },
                    saveGarment = {
                        val garment = Garment(
                            type = garmentType!!,
                            color = (viewState.value as ConfirmPhotoViewState.Ready).selectedColor,
                            photoUriString = photoUriString
                        )
                        confirmPhotoViewModel.saveGarmentToDB(garment = garment)

                        navController.popBackStack(Routes.HOME.value, false)
                        navController.navigate(Routes.CLOSET.value)
                    },
                    retakePhoto = {
                        navController.popBackStack()
                    },
                    onColorSelect = { color ->
                        confirmPhotoViewModel.updateSelectedColor(color = color)
                    }
                )
            }.onFailure {
                // TODO: handle all failures
            }
        }
    }
}