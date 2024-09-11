package com.example.weather_app.models

import java.util.Date

data class Current(
    var time: Date,
    var temperature_2m: Double
)
