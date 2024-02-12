package com.example.weathercomposeapp.screens.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercomposeapp.data.Response
import com.example.weathercomposeapp.domain.location.LocationTracker
import com.example.weathercomposeapp.domain.repository.WeatherRepository
import com.example.weathercomposeapp.model.LocationData
import com.example.weathercomposeapp.model.weather.WeatherResult
import com.example.weathercomposeapp.repository.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class State {
    LOADING,
    SUCCESS,
    FAILED
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())

    fun getWeatherData(
        onLocationRetrieved: (LocationData) -> Unit = {},
        onWeatherRetrieved: (WeatherResult) -> Unit = {}
    ) {
        Log.d("TEST_ONLY", "getWeatherData: Call getWeatherData")
        viewModelScope.launch {

            state = state.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                val locationData = LocationData(lat = location.latitude, long = location.longitude)
                onLocationRetrieved(locationData)
                when (val result = repository.getWeather(locationData)) {
                    is Response.Success -> {
                        result.data?.let {
                            onWeatherRetrieved(it)
                        }
                        state = state.copy(
                            weatherResult = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Response.Error -> {
                        state = state.copy(
                            weatherResult = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    // Used only by existing location
    fun getWeatherDataByLocation(locationData: LocationData) {
        Log.d("TEST_ONLY", "getWeatherDataByLocation: $locationData")
        viewModelScope.launch {

            state = state.copy(
                isLoading = true,
                error = null
            )

            when (val result = repository.getWeather(locationData)) {
                is Response.Success -> {
                    state = state.copy(
                        weatherResult = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Response.Error -> {
                    state = state.copy(
                        weatherResult = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun getWeatherDataByCity(
        city: String,
        onLocationRetrieved: (LocationData) -> Unit = {},
        onWeatherRetrieved: (WeatherResult) -> Unit = {}
    ) {
        Log.d("TEST_ONLY", "getWeatherDataByCity: $city")
        viewModelScope.launch {

            state = state.copy(
                isLoading = true,
                error = null
            )

            when (val result = repository.getWeatherByCity(city)) {
                is Response.Success -> {
                    val lat = result.data?.coord?.lat ?: 0.0
                    val lon = result.data?.coord?.lon ?: 0.0
                    val locationData = LocationData(lat = lat, long = lon)
                    onLocationRetrieved(locationData)

                    result.data?.let {
                        onWeatherRetrieved(it)
                    }

                    state = state.copy(
                        weatherResult = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Response.Error -> {
                    state = state.copy(
                        weatherResult = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}