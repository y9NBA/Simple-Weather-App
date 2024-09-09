package com.example.weather_app

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.secret.ApiKey
import com.example.weather_app.repositories.ApiConfig
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.models.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var apiKey : ApiKey = ApiKey()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            btnEnter.setOnClickListener {
                val client = ApiConfig.getApiService().getWeatherInfo(
                    enterLocation.text.toString(),
                    apiKey.getWeatherApi()
                )

                client?.enqueue(object: Callback<Weather?> {
                    override fun onResponse(
                        call: Call<Weather?>,
                        response: Response<Weather?>
                    ) {
                        Log.e("Response", "Response not null")
                        if (response.isSuccessful) {
                            Log.e("Response", "Response is success")
                        } else {
                            Log.e("Response", "Response not is success")
                        }
                        response.body()?.let {
                            updateUI(it)
                        }
                    }

                    override fun onFailure(call: Call<Weather?>, t: Throwable) {
                        Log.e("ResponseOnFailure", "${t.message}")
                        Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weather: Weather) {
        val binding = ActivityMainBinding.inflate(layoutInflater)

        Log.e("UI", "UPDATE is PROCESSING")

        with(binding) {
            with(weather) {
                val tempText = temp.toString()
                val humidityText = humidity.toString()
                var descriptionText = "description"

                if (description != null) {
                    descriptionText = description as String
                }

                if (icon != null) {
                    val iconUrl = Uri.parse("https://openweathermap.org/img/w/${icon as String}.png")
                    iconWeather.setImageURI(iconUrl)
                } else {
                    iconWeather.setImageDrawable(Drawable.createFromPath("res/drawable/ic_launcher_foreground.xml"))
                }

                weatherLocation.text = tempText + "\n" + humidityText + "\n" + descriptionText

                codText.text = weather.cod

                Log.e("UI", "UPDATE is DONE")
            }
        }
    }
}