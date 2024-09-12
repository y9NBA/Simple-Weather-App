package com.example.weather_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.controllers.WeatherController
import com.example.weather_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val weatherController = WeatherController()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            val sharedPref = this@MainActivity.getSharedPreferences(getString(R.string.currCity), MODE_PRIVATE)

            Log.e("CreateMain", "currCity: ${sharedPref.getString(getString(R.string.currCity), "Not city")}; saveCity: ${getString(R.string.saveCity)}")

            sharedPref.getString(
                getString(R.string.currCity),
                getString(R.string.saveCity)
            )?.let {
                updateWeatherUI(it)
            }

            btnNext.setOnClickListener {
                val intent = Intent(this@MainActivity, LocationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateWeatherUI(location: String) {
        with(binding) {
            weatherController.getWeather(location, context = this@MainActivity)

            weatherController.weather.observe(this@MainActivity) {
                cityText.text = it.cityName
                weatherLocation.text =
                    "${it.current.temperature_2m} ${it.current_units.temperature_2m}"
            }
        }
    }
}