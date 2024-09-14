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

            val sharedPref = this@MainActivity.getSharedPreferences(getString(R.string.prefData), MODE_PRIVATE)

            if (sharedPref.getString(getString(R.string.saveCity), null) == null) {
                with(sharedPref.edit()) {
                    putString(
                        getString(R.string.saveCity),
                        getString(R.string.myCity)
                    )
                    apply()
                }
            }

            Log.e(
                "CreateMain",
                "currCity: ${sharedPref.getString(getString(R.string.currCity), null)}; saveCity: ${sharedPref.getString(getString(R.string.saveCity), null)}"
            )

            sharedPref.getString(
                getString(R.string.currCity),
                sharedPref.getString(
                    getString(R.string.saveCity),
                    null
                )
            )?.let {
                updateWeatherUI(it)
            }

            btnNext.setOnClickListener {
                val intent = Intent(this@MainActivity, LocationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        clearCurrentCity(this)

        Log.e("onStart", "Processing")
    }

    override fun onStop() {
        super.onStop()
//        clearCurrentCity(this)

        Log.e("onStop", "Processing")
    }

    override fun onRestart() {
        super.onRestart()
//        clearCurrentCity(this)

        Log.e("onRestart", "Processing")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("AppDestroy", "start cleaning...")

        clearCurrentCity(this)
        clearListCity(this)

        Log.e("AppDestroy", "currCity and listCity is clean")
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

fun clearCurrentCity(context: Context) {
    with(context) {
        val sharedPref = getSharedPreferences(
            getString(R.string.prefData),
            AppCompatActivity.MODE_PRIVATE
        )

        with(sharedPref.edit()) {
            putString(getString(R.string.currCity), null)
            apply()
        }
    }
}

fun clearListCity(context: Context) {
    with(context) {
        val sharedPref = getSharedPreferences(
            getString(R.string.prefData),
            AppCompatActivity.MODE_PRIVATE
        )

        with(sharedPref.edit()) {
            putStringSet(getString(R.string.listCity), null)
            apply()
        }
    }
}
