package com.example.weathercomposeapp.model.forecast


import com.google.gson.annotations.SerializedName

data class ForecastResult(
    @SerializedName("city") val city: City,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("cod") val cod: String,
    @SerializedName("list") val list: List<ForecastItem>,
    @SerializedName("message") val message: Int
)