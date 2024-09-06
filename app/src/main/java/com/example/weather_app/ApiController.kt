package com.example.weather_app

import com.example.secret.ApiKey

class ApiController(private var location: String) {

    var apiKey = ApiKey().getWeatherApi()

    fun getWeatherInfo(location: String) {

    }
}