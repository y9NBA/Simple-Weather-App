package com.example.weather_app.config

import com.example.weather_app.services.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): WeatherService {
            val api = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return api.create(WeatherService::class.java)
        }
    }
}