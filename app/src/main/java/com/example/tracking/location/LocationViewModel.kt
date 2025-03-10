package com.example.tracking.location


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocationViewModel(context: Context) : ViewModel() {
    private val db = LocationDatabase.getDb(context.applicationContext)

    private val _state = MutableStateFlow(Location(0.0, 0.0))
    val state: StateFlow<Location> = _state.asStateFlow()

    private val _path = MutableStateFlow<List<Location>>(emptyList())
    val path = _path.asStateFlow()

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime.asStateFlow()

    private val _distance = MutableStateFlow(0.0)
    val distance: StateFlow<Double> = _distance.asStateFlow()

    private var tracking = false

    init {
        loadLastLocation()
    }

    private fun loadLastLocation() {
        viewModelScope.launch {

            val lastLocation = db.getDao().getLastLocation()
            if (lastLocation != null) {
                _state.value = lastLocation
                _path.value = db.getDao().getAllLocations()
            }
        }
    }

    fun startTracking() {
        if (!tracking) {
            tracking = true
            _elapsedTime.value = 0L
            _distance.value = 0.0
            Log.d("viewmodelvalues", "elapsedTime.value $_elapsedTime distance.value $_distance")
            startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (tracking) {
                delay(1000)
                _elapsedTime.value += 1
            }
        }
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        _state.value = Location(latitude, longitude)
        saveLocation(_state.value)
    }

    fun stopTracking() {
        tracking = false
        if (_path.value.size > 1) {
            val start = _path.value.first()
            val end = _path.value.last()
            _distance.value = calculateDistance(start, end)
        }
    }

    private fun saveLocation(location: Location) {
        viewModelScope.launch {
            db.getDao().insertLocation(Location(location.latitude, location.longitude))
        }
        _path.value += location
        Log.d("PATH VALUES", "${_path.value} values")
    }

    private fun calculateDistance(startLocation: Location, endLocation: Location): Double {
        val radius = 6371
        val dLat = Math.toRadians(endLocation.latitude - startLocation.latitude)
        val dLon = Math.toRadians(endLocation.longitude - startLocation.longitude)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(startLocation.latitude)) * cos(
            Math.toRadians(endLocation.latitude)
        ) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return radius * c
    }

    fun clearLocations() {
        viewModelScope.launch {
            val lastLocation = db.getDao().getLastLocation()
            db.getDao().clearAllLocations()
            if (lastLocation != null) {
                updateLocation(lastLocation.latitude, lastLocation.longitude)
            } else {
                val lastKnownLocation = _state.value
                updateLocation(lastKnownLocation.latitude, lastKnownLocation.longitude)
            }
        }
        _path.value = emptyList()
    }
}
