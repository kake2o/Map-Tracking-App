package com.example.tracking.location

import android.content.Context
import kotlinx.coroutines.flow.Flow

class LocationRepository(context: Context) {
    private val locationDatabase = LocationDatabase.getDb(context)

    fun getAllLocations(): Flow<List<Location>> {
        return locationDatabase.getDao().getAllLocations()
    }

    suspend fun insertLocation(latitude: Double, longitude: Double) {
        locationDatabase.getDao().insertLocation(Location(latitude, longitude))
    }

    suspend fun clearLocations() {
        locationDatabase.getDao().clearAllLocations()
    }
}