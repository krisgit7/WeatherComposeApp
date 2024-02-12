package com.example.weathercomposeapp.repository.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.example.weathercomposeapp.model.LocationData
import com.example.weathercomposeapp.model.weather.WeatherResult
import com.google.gson.Gson

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyLocationPrefs", Context.MODE_PRIVATE)

    fun saveLocation(locationData: LocationData) {
        val editor = sharedPreferences.edit()
        editor.putString(LATITUDE_KEY, locationData.lat.toString())
        editor.putString(LONGITUDE_KEY, locationData.long.toString())
        editor.apply()
    }

    fun getLocation(): LocationData? {
        val latString = (sharedPreferences.getString(LATITUDE_KEY, DEFAULT_VALUE) ?: DEFAULT_VALUE)
        val longString = (sharedPreferences.getString(LONGITUDE_KEY, DEFAULT_VALUE) ?: DEFAULT_VALUE)

        if (latString == DEFAULT_VALUE && longString == DEFAULT_VALUE) {
            return null
        }

        val lat = latString.toDouble()
        val long = longString.toDouble()
        return LocationData(lat, long)
    }

    fun saveWeatherResult(weatherResult: WeatherResult) {
        val editor = sharedPreferences.edit()
        val jsonString = gson.toJson(weatherResult)
        editor.putString(WEATHER_RESULT_KEY, jsonString)
        editor.apply()
    }

    fun getWeatherResult(): WeatherResult? {
        val jsonString = sharedPreferences.getString(WEATHER_RESULT_KEY, DEFAULT_VALUE)
        if (jsonString == DEFAULT_VALUE) {
            return null
        }

        val weatherResult = gson.fromJson(jsonString, WeatherResult::class.java)
        return weatherResult
    }

    companion object {
        const val LATITUDE_KEY = "LAT"
        const val LONGITUDE_KEY = "LONG"
        const val WEATHER_RESULT_KEY = "WEATHER_RESULT"
        const val DEFAULT_VALUE = ""
        private val gson = Gson()
    }
}