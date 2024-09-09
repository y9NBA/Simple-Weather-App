package com.example.weather_app.models

data class Weather(
    var temp : Double?,
    var humidity : Int?,
    var description : String?,
    var icon : String?,
    var cod : String,
    var message : String?
)
