package com.example.weathercomposeapp.utils

class Constants {

    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val BASE_ICON_URL = "https://openweathermap.org/img/wn/"
        const val API_KEY = "f472df110d368e296e6ab6ff4919228a"
        const val LOADING_TEXT = "Loading..."
        const val DEFAULT_CITY = "NULL"
    }
}