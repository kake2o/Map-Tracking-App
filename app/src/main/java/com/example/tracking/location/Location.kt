package com.example.tracking.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Locations")
data class Location(
    @ColumnInfo(name = "latitude")
    var  latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
