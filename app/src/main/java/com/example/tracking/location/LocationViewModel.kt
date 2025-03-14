package com.example.tracking.location


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocationViewModel(context: Context) : ViewModel() {
    private val locationRepository = LocationRepository(context)

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()

    private val _locationHistory = MutableStateFlow<List<Location>>(emptyList())
    val locationHistory: StateFlow<List<Location>> = _locationHistory.asStateFlow()

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime.asStateFlow()

    private val _distance = MutableStateFlow(0.0)
    val distance: StateFlow<Double> = _distance.asStateFlow()

    private var tracking = false
    init {
        observeLocationHistory()
    }

    private fun observeLocationHistory() {
        viewModelScope.launch {
            locationRepository
                .getAllLocations()
                .distinctUntilChanged()
                .collect { locations ->
                    _locationHistory.value = locations
                    _currentLocation.value = locations.lastOrNull()
                }
        }
    }

    fun startTracking() {
        if (!tracking) {
            tracking = true
            _elapsedTime.update { 0L }
            _distance.update { 0.0 }
            startTimer()
            startLocationUpdates()
        }
        clearLocations()
    }

    private fun startLocationUpdates() {
        viewModelScope.launch {
            while (tracking) {
                delay(5000)
                observeLocationHistory()
                if (_locationHistory.value.size > 1) {
                    _distance.update {
                        _distance.value + calculateDistance(
                            _locationHistory.value.last(),
                            _locationHistory.value[_locationHistory.value.size - 2]
                        )
                    }
                }
            }
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

    fun stopTracking(){
        tracking = false
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


    private fun clearLocations() {
        viewModelScope.launch {
            locationRepository.clearLocations()
        }
        _locationHistory.value = emptyList()
    }
}