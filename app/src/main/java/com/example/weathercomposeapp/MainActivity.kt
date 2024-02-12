package com.example.weathercomposeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.weathercomposeapp.navigation.WeatherNavigation
import com.example.weathercomposeapp.repository.sharedpreference.PreferencesManager
import com.example.weathercomposeapp.screens.main.MainScreen
import com.example.weathercomposeapp.screens.main.MainViewModel
import com.example.weathercomposeapp.ui.theme.WeatherComposeAppTheme
import com.example.weathercomposeapp.utils.Constants.Companion.permissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
//    private lateinit var preferencesManager: PreferencesManager
//    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        preferencesManager = PreferencesManager(this.applicationContext)
//        val currentLocationData = preferencesManager.getLocation()
//
//        permissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) {
//            if (currentLocationData != null) {
//                Log.d("TEST_ONLY", "onCreate getWeatherDataByLocation: $currentLocationData")
//                mainViewModel.getWeatherDataByLocation(currentLocationData)
//            } else {
//                Log.d("TEST_ONLY", "onCreate getWeatherData")
//                mainViewModel.getWeatherData(onLocationRetrieved = {
//                    preferencesManager.saveLocation(it)
//                })
//            }
//        }
//        permissionLauncher.launch(permissions)
        setContent {
            WeatherComposeAppTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherNavigation()
        }
    }
}
