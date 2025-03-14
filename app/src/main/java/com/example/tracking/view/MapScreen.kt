package com.example.tracking.view


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tracking.location.LocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(paddingValues: PaddingValues, viewModel: LocationViewModel) {

    val cameraPositionState = rememberCameraPositionState()
    val currentLocation by viewModel.currentLocation.collectAsState()

    val locationHistory by viewModel.locationHistory.collectAsState()

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            val cameraPosition =
                CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        cameraPositionState = cameraPositionState
    ) {
        Polyline(
            color = Color.Red,
            points = locationHistory.map { LatLng(it.latitude, it.longitude) }
        )
    }
}
