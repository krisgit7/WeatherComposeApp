package com.example.weathercomposeapp.domain.repository

import com.example.weathercomposeapp.data.Response
import com.example.weathercomposeapp.model.LocationData
import com.example.weathercomposeapp.model.weather.WeatherResult

interface WeatherRepository {

    suspend fun getWeather(locationData: LocationData): Response<WeatherResult>
    suspend fun getWeatherByCity(city: String): Response<WeatherResult>
}