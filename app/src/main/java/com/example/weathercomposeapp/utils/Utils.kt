package com.example.weathercomposeapp.utils

import com.example.weathercomposeapp.utils.Constants.Companion.BASE_ICON_URL
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {
        fun buildImageUrl(icon: String): String {
            return "$BASE_ICON_URL$icon.png"
        }
    }
}