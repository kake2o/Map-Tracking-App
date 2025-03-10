package com.example.tracking.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TrackingInfoOverlay(elapsedTime: Long, distance: Double, modifier: Modifier) {
    val minutes = elapsedTime / 60
    val seconds = elapsedTime % 60
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Column {
            Text(
                text = "Time: %02d:%02d".format(minutes, seconds),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Distance:  %.2f km".format(distance),
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

