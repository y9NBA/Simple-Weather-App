package com.example.weather_app.repositories

import com.example.weather_app.models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeatherInfo(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): Call<Weather?>?
}