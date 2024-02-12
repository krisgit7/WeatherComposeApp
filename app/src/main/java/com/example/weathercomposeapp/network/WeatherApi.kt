package com.example.weathercomposeapp.network

import com.example.weathercomposeapp.model.forecast.ForecastResult
import com.example.weathercomposeapp.model.weather.WeatherResult
import com.example.weathercomposeapp.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = API_KEY
    ): WeatherResult

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = API_KEY
    ): WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = API_KEY
    ): ForecastResult
}