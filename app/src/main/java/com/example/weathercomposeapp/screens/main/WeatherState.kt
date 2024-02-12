package com.example.weathercomposeapp.screens.main

import com.example.weathercomposeapp.model.weather.WeatherResult

data class WeatherState(
    val weatherResult: WeatherResult? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
