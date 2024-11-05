package com.example.eswapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.eswapp.ui.theme.ESWAppTheme
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.CameraPosition


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ESWAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GoogleMapScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GoogleMapScreen(modifier: Modifier = Modifier) {
    // Coordinates of College Station, TX
    val collegeStation = LatLng(30.62798, -96.33441)

    // Camera position state for controlling the camera view
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(collegeStation, 15f)
    }

    // List of markers (LatLng coordinates) (negative for south and west)
    val markerPositions = listOf(
        LatLng(30.62798, -96.33441), // College Station, TX
        LatLng(30.6129, -96.3423),   // MSC
        LatLng(30.6, -96.34),   // test marker
    )

    // Render Google Map using Compose
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Loop through each marker position and place a marker on the map
        markerPositions.forEach { position ->
            Marker(
                state = MarkerState(position = position),
                title = "Marker at ${position.latitude}, ${position.longitude}",
                snippet = "This is a description of the marker."
            )
        }
    }
}



//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ESWAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android Studio",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ESWAppTheme {
//        Greeting("Android")
//    }
//}