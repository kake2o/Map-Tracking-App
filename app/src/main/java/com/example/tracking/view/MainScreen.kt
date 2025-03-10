package com.example.tracking.view


import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tracking.location.LocationViewModel


@Composable
fun MainScreen(
    viewModel: LocationViewModel,
    context: Context
) {

    val currentLocation by viewModel.state.collectAsState()
    val path by viewModel.path.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val distance by viewModel.distance.collectAsState()

    LaunchedEffect(currentLocation) {
        Log.d("MainScreen", "Current Location: $currentLocation")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                context = context.applicationContext,
                viewModel = viewModel,
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Map(
                paddingValues,
                currentLocation,
                path
            )
            TrackingInfoOverlay(elapsedTime, distance, Modifier.align(Alignment.TopEnd))
        }
    }
}
