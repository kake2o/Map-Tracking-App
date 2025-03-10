package com.example.tracking.location

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LocationDao {

    @Upsert
    suspend fun insertLocation(location: Location)

    @Query("DELETE FROM Locations")
    suspend fun clearAllLocations()

    @Query("SELECT * FROM Locations")
    suspend fun getAllLocations(): List<Location>

    @Query("SELECT * FROM Locations ORDER BY id DESC LIMIT 1")
    suspend fun getLastLocation(): Location?
}