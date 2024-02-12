package com.example.weathercomposeapp.screens.main

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.model.weather.WeatherResult
import com.example.weathercomposeapp.navigation.WeatherScreens
import com.example.weathercomposeapp.repository.sharedpreference.PreferencesManager
import com.example.weathercomposeapp.utils.Constants
import com.example.weathercomposeapp.utils.Constants.Companion.DEFAULT_CITY
import com.example.weathercomposeapp.utils.Constants.Companion.LOADING_TEXT
import com.example.weathercomposeapp.utils.Utils.Companion.buildImageUrl
import com.example.weathercomposeapp.utils.formatDate
import com.example.weathercomposeapp.widgets.WeatherAppBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
) {
    val preferencesManager = PreferencesManager(LocalContext.current)
    val currentLocationData = preferencesManager.getLocation()

    val fineLocationPermissionState = rememberMultiplePermissionsState(
        Constants.permissions.toList()
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (!city.isNullOrEmpty() && city != DEFAULT_CITY) {
            Log.d("TEST_ONLY", "onCreate getWeatherDataByCity: $city")
            mainViewModel.getWeatherDataByCity(city,
                onLocationRetrieved = {
                    preferencesManager.saveLocation(it)
                },
                onWeatherRetrieved = {
                    preferencesManager.saveWeatherResult(it)
                })
            return@rememberLauncherForActivityResult
        }
        if (currentLocationData != null) {
            Log.d("TEST_ONLY", "onCreate getWeatherDataByLocation: $currentLocationData")
            mainViewModel.getWeatherDataByLocation(currentLocationData)
        } else {
            Log.d("TEST_ONLY", "onCreate getWeatherData")
            mainViewModel.getWeatherData(
                onLocationRetrieved = {
                    preferencesManager.saveLocation(it)
                },
                onWeatherRetrieved = {
                    preferencesManager.saveWeatherResult(it)
                }
            )
        }
    }

    LaunchedEffect(fineLocationPermissionState) {
        permissionLauncher.launch(Constants.permissions)
    }

    if (mainViewModel.state.isLoading) {
        LoadingSection()
    } else if (!mainViewModel.state.error.isNullOrEmpty()) {
        val currentWeather = preferencesManager.getWeatherResult()
        Log.d("TEST_ONLY", "onCreate currentWeather: $currentWeather")
        if (currentWeather != null) {
            LocationScreen(navController = navController, weatherResult = currentWeather)
            Toast.makeText(LocalContext.current, "Can't find city weather", Toast.LENGTH_SHORT).show()
        } else {
            ErrorSection(errorMessage = mainViewModel.state.error!!)
        }
    } else {
        val weatherResult = mainViewModel.state.weatherResult
        if (weatherResult != null) {
            LocationScreen(navController = navController, weatherResult = weatherResult)
        } else {
            ErrorSection(errorMessage = "No location provided")
        }
    }
}

@Composable
fun LocationScreen(
    navController: NavController,
    weatherResult: WeatherResult
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "${weatherResult.name}, ${weatherResult.sys?.country}",
                navController = navController,
                onSearchActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                elevation = 5.dp
            ) {
                Log.d("TAG", "MainScaffold: Button Clicked")

            }
        }
    ) {
        Surface(modifier = Modifier.padding(
            top = it.calculateTopPadding()
        )) {
            MainContent(weatherResult)
        }
    }
}

@Composable
fun MainContent(weatherResult: WeatherResult) {
    var title = ""
    if (!weatherResult.name.isNullOrEmpty()) {
        weatherResult.name.let {
            title = it
        }
    } else {
        weatherResult.coord?.let {
            title = "${it.lat}/${it.lon}"
        }
    }

    var subTitle = ""
    val dateVal = (weatherResult.dt ?: 0)
    subTitle = if (dateVal == 0) LOADING_TEXT else formatDate(dateVal)

    var icon = ""
    var description = ""
    weatherResult.weather?.let {
        if (it.size > 0) {
            description = if (it[0].description == null) LOADING_TEXT else it[0].description
            icon = if (it[0].icon == null) LOADING_TEXT else it[0].icon
        }
    }

    var temp = ""
    var humidity = ""
    var pressure = ""
    weatherResult.main?.let {
        temp = "${it.temp} °C"
        humidity = "${it.humidity}%"
        pressure = "${it.pressure} psi"
    }

    var wind = ""
    weatherResult.wind.let {
        wind = if (it == null) LOADING_TEXT else "${it.speed} m/s"
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherTitleSection(text = title, subText = subTitle, fontSize = 30.sp, subFontSize = 15.sp)
        WeatherImage(imageUrl = buildImageUrl(icon))
        WeatherTitleSection(
            text = temp,
            subText = description,
            fontSize = 60.sp,
            subFontSize = 15.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherInfo(iconId = R.drawable.wind, wind)
            WeatherInfo(iconId = R.drawable.pressure, pressure)
            WeatherInfo(iconId = R.drawable.humidity, humidity)
        }
    }
}

@Composable
fun WeatherTitleSection(text: String, subText: String, fontSize: TextUnit, subFontSize: TextUnit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = fontSize, color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = subText, fontSize = subFontSize, color = Color.Black)
    }
}

@Composable
fun WeatherImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun WeatherInfo(@DrawableRes iconId: Int, text: String) {
    Row(modifier = Modifier.padding(4.dp)) {
        Icon(painter = painterResource(id = iconId),
            contentDescription = "humidity icon",
            modifier = Modifier.size(20.dp))
        Text(text = text,
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ErrorSection(errorMessage: String) {
    Text(text = errorMessage, color = Color.Black)
}

@Composable
fun LoadingSection() {
    CircularProgressIndicator(color = Color.Black)
}