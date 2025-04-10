package com.example.eswapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.eswapp.ui.theme.ESWAppTheme
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

data class MarkerInfo(
    val position: LatLng,
    val title: String,
    val snippet: String
)

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

    // Define marker information list
    val markerList = listOf(
        MarkerInfo(
            position = LatLng(30.62798, -96.33441),
            title = "College Station",
            snippet = "Home of Texas A&M University."
        ),
        MarkerInfo(
            position = LatLng(30.6129, -96.3423),
            title = "Memorial Student Center",
            snippet = "Popular student center at Texas A&M."
        ),
        MarkerInfo(
            position = LatLng(30.6, -96.34),
            title = "Test Marker",
            snippet = "Description for a test marker."

        )
    )

    // State to hold the selected marker info
    val selectedMarker = remember { mutableStateOf<MarkerInfo?>(null) }



    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Place markers on the map and handle click events
        markerList.forEach { markerInfo ->
            Marker(
                state = MarkerState(position = markerInfo.position),
                title = markerInfo.title,
                snippet = markerInfo.snippet,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.recycle),
                onClick = {
                    selectedMarker.value = markerInfo
                    true // Consume the click event
                }
            )
        }
    }

    // Display custom info window if a marker is selected
    selectedMarker.value?.let { markerInfo ->
        CustomInfoWindowForMarker(markerInfo)
    }
}

@Composable
fun CustomInfoWindowForMarker(markerInfo: MarkerInfo) {
    when (markerInfo.title) {
        "College Station" -> {
            AndroidView(
                factory = { context ->
                    LayoutInflater.from(context).inflate(R.layout.info_window_college_station, null)
                },
                update = { view ->
                    view.findViewById<TextView>(R.id.marker_title).text = markerInfo.title
                    view.findViewById<TextView>(R.id.marker_snippet).text = markerInfo.snippet
                }
            )
        }
        "Memorial Student Center" -> {
            AndroidView(
                factory = { context ->
                    LayoutInflater.from(context).inflate(R.layout.info_window_msc, null)
                },
                update = { view ->
                    view.findViewById<TextView>(R.id.marker_title).text = markerInfo.title
                    view.findViewById<TextView>(R.id.marker_snippet).text = markerInfo.snippet
                }
            )
        }
        "Test Marker" -> {
            AndroidView(
                factory = { context ->
                    LayoutInflater.from(context).inflate(R.layout.info_window_test_marker, null)
                },
                update = { view ->
                    view.findViewById<TextView>(R.id.marker_title).text = markerInfo.title
                    view.findViewById<TextView>(R.id.marker_snippet).text = markerInfo.snippet
                }
            )
        }
    }
}




//package com.example.eswapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.example.eswapp.ui.theme.ESWAppTheme
////import com.google.accompanist.permissions.ExperimentalPermissionsApi
////import com.google.accompanist.permissions.rememberPermissionState
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.*
//import com.google.android.gms.maps.model.CameraPosition
//
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ESWAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    GoogleMapScreen(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun GoogleMapScreen(modifier: Modifier = Modifier) {
//    // Coordinates of College Station, TX
//    val collegeStation = LatLng(30.62798, -96.33441)
//
//    // Camera position state for controlling the camera view
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(collegeStation, 15f)
//    }
//
//    // List of markers (LatLng coordinates) (negative for south and west)
//    val markerPositions = listOf(
//        LatLng(30.62798, -96.33441), // College Station, TX
//        LatLng(30.6129, -96.3423),   // MSC
//        LatLng(30.6, -96.34),   // test marker
//    )
//
//    // Render Google Map using Compose
//    GoogleMap(
//        modifier = modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState
//    ) {
//        // Loop through each marker position and place a marker on the map
//        markerPositions.forEach { position ->
//            Marker(
//                state = MarkerState(position = position),
//                title = "Marker at ${position.latitude}, ${position.longitude}",
//                snippet = "This is a description of the marker."
//            )
//        }
//    }
//}
