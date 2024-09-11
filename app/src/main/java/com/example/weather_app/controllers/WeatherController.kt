package com.example.weather_app.controllers

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather_app.models.Weather
import com.example.weather_app.repositories.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherController {
    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    fun getWeather(location: String,
                   current: String = "temperature_2m",
                   hourly: String = "temperature_2m,relative_humidity_2m,weather_code",
                   daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
                   context: Context) {

        val geolocation = getLocation(context, location)
        val cityName = geolocation.third
        val client = ApiConfig.getApiService().getWeatherInfo(geolocation.first, geolocation.second, current, hourly, daily)

        client?.enqueue(object : Callback<Weather?> {
            override fun onResponse(call: Call<Weather?>, response: Response<Weather?>) {
                if (response.isSuccessful) {
                    Log.wtf("Response", "Response is successful")
                    response.body()?.let {
                        it.cityName = cityName.first!!
                        it.fullCityName = cityName.second!!
                        _weather.value = it
                    }
                } else {
                    Log.e("Response", "Response is not OK")
                }
            }

            override fun onFailure(call: Call<Weather?>, t: Throwable) {
                Toast.makeText(context, "Не удалось получить данные сервера\nТакая локация не найдена", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getLocation(context: Context, location: String): Triple<Double?, Double?, Pair<String?, String?>> {
        val geocoder = Geocoder(context)
        val address = geocoder.getFromLocationName(location,1)!!

        return if (address.size > 0) {
            Triple(address[0].latitude, address[0].longitude,
                Pair(address[0].featureName,
                    address[0].featureName + ", " + address[0].adminArea + ", " + address[0].locale.displayCountry
                ))
        } else {
            Triple(null, null, Pair(null, null))
        }
    }
}