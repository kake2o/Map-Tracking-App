package com.example.tracking.location

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Upsert
    suspend fun insertLocation(location: Location)

    @Query("DELETE FROM Locations")
    suspend fun clearAllLocations()

    @Query("SELECT * FROM Locations")
    fun getAllLocations(): Flow<List<Location>>

    @Query("SELECT * FROM Locations ORDER BY id DESC LIMIT 1")
    suspend fun getLastLocation(): Location?
}