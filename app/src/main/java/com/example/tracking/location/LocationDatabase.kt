package com.example.tracking.location

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Location::class],
    version = 1
)
abstract class LocationDatabase: RoomDatabase() {
    companion object {
        fun getDb(context: Context): LocationDatabase {
            return Room.databaseBuilder(
                context = context.applicationContext,
                LocationDatabase::class.java,
                "Location.db"
            ).build()
        }
    }
    abstract fun getDao(): LocationDao
}