package com.example.weathercomposeapp.repository

import com.example.weathercomposeapp.data.Response
import com.example.weathercomposeapp.domain.repository.WeatherRepository
import com.example.weathercomposeapp.model.LocationData
import com.example.weathercomposeapp.model.weather.WeatherResult
import com.example.weathercomposeapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi): WeatherRepository {

    override suspend fun getWeather(locationData: LocationData): Response<WeatherResult> {
        return try {
            Response.Success(
                data = api.getWeather(lat = locationData.lat, lon = locationData.long)
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error.")
        }
    }

    override suspend fun getWeatherByCity(city: String): Response<WeatherResult> {
        return try {
            Response.Success(
                data = api.getWeatherByCity(query = city)
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error.")
        }
    }
}