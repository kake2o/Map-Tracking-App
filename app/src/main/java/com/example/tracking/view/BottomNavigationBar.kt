package com.example.tracking.view


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tracking.location.LocationViewModel
import com.example.tracking.bottom_navigation.BottomItem
import com.example.tracking.location.LocationService


@Composable
fun BottomBar(
    context: Context,
    viewModel: LocationViewModel,

    ) {
    BottomAppBar(modifier = Modifier.fillMaxWidth()) {
        BottomItem.btmItem.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = {
                    when (item.name) {
                        "Start" -> {
                            viewModel.clearLocations()
                            viewModel.startTracking()
                            val intent =
                                Intent(
                                    context.applicationContext,
                                    LocationService::class.java
                                ).apply {
                                    action = "crash"
                                }
                            context.startService(intent)
                        }

                        "Stop" -> {
                            viewModel.stopTracking()
                            val intent =
                                Intent(context.applicationContext, LocationService::class.java)
                            context.stopService(intent)
                        }
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.name)
                },
                label = { Text(text = item.name) }
            )
        }
    }
}