package com.example.weather_app

import com.example.secret.ApiKey

class ApiController(private var location: String) {

    val apiKey = ApiKey().getWeatherApi()

    fun getWeatherInfo(location: String) {

    }
}