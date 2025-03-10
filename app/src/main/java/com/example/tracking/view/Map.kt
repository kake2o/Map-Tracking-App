package com.example.tracking.view


import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tracking.location.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun Map(paddingValues: PaddingValues, currentLocation: Location, path: List<Location>) {
    val cameraPositionState = rememberCameraPositionState()

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        cameraPositionState = cameraPositionState
    ) {
        LaunchedEffect(currentLocation) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(currentLocation.latitude, currentLocation.longitude),
                10f
            )
            Log.d(
                "Camera Update",
                "Camera position updated: ${currentLocation.latitude}, ${currentLocation.longitude}"
            )
        }
        Polyline(
            color = Color.Red,
            points = path.map { LatLng(it.latitude, it.longitude) }
        )

        Circle(
            center = LatLng(currentLocation.latitude, currentLocation.longitude),
            radius = 5.0,
            fillColor = Color(0x5500FF00),
            strokeColor = Color.Green,
            strokeWidth = 2f
        )
    }
}
