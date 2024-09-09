package com.example.weather_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.controllers.WeatherController
import com.example.weather_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val weatherController = WeatherController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            btnEnter.setOnClickListener {
                weatherController.getWeather(enterLocation.text.toString(), context = this@MainActivity)

                weatherController.weather.observe(this@MainActivity) {
                    weatherLocation.text = it.current.temperature_2m.toString()
                }
            }
        }
    }
}