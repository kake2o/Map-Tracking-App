package com.example.tracking.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow

object BottomItem {
    val btmItem = listOf(
        btmItem(
            name = "Start",
            icon = Icons.Default.PlayArrow
        ),
        btmItem(
            name = "Stop",
            icon = Icons.Default.Close
        )
    )
}