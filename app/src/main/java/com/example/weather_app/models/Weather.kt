package com.example.weather_app.models

data class Weather(
    var cityName: String,
    var fullCityName: String,
    var current: Current,
    var current_units: CurrentUnits
)
