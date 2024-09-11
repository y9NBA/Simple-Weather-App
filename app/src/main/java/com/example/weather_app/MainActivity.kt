package com.example.weather_app

import android.os.Bundle
import android.widget.Toast
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

                val location = enterLocation.text.toString()

                if (location.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Введите локацию\nПоле ввода пустое", Toast.LENGTH_SHORT).show()
                } else {

                    weatherController.getWeather(location, context = this@MainActivity)

                    weatherController.weather.observe(this@MainActivity) {
                        cityText.text = it.cityName
                        weatherLocation.text = "${it.current.temperature_2m} ${it.current_units.temperature_2m}"
                    }
                }
            }
        }
    }
}