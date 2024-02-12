package com.example.weathercomposeapp.model.forecast


import com.example.weathercomposeapp.model.weather.Clouds
import com.example.weathercomposeapp.model.weather.Weather
import com.example.weathercomposeapp.model.weather.Wind
import com.google.gson.annotations.SerializedName

data class ForecastItem(
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("dt") val dt: Int,
    @SerializedName("dt_txt") val dtTxt: String,
    @SerializedName("main") val main: Main,
    @SerializedName("pop") val pop: Double,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind
)