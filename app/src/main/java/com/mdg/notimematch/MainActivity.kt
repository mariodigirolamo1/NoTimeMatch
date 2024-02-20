package com.mdg.notimematch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mdg.notimematch.camera.CameraViewModel
import com.mdg.notimematch.closet.ClosetViewModel
import com.mdg.notimematch.confirmphoto.ConfirmPhotoViewModel
import com.mdg.notimematch.ui.theme.NoTimeMatchTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val closetViewModel: ClosetViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()
    private val confirmPhotoViewModel: ConfirmPhotoViewModel by viewModels()

    private lateinit var cameraExecutor: ExecutorService

    // TODO: this is a very basic implementation of this permission check
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    if(it.key == STORAGE_PERMISSION){
                        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R){
                            permissionGranted = false
                        }
                    }
            }
            if (!permissionGranted) {
                Toast.makeText(baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        cameraExecutor = Executors.newSingleThreadExecutor()

        setContent {
            NoTimeMatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoTimeMatchApp(
                        closetViewModel = closetViewModel,
                        cameraViewModel = cameraViewModel,
                        confirmPhotoViewModel = confirmPhotoViewModel,
                        outputDirectory = filesDir,
                        cameraExecutor = cameraExecutor
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private companion object {
        private const val STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"
        private const val CAMERA_PERMISSION = "android.permission.CAMERA"
        private const val AUDIO_PERMISSION = "android.permission.RECORD_AUDIO"

        private val REQUIRED_PERMISSIONS = arrayOf(
            STORAGE_PERMISSION,
            CAMERA_PERMISSION,
            AUDIO_PERMISSION
        )
    }
}