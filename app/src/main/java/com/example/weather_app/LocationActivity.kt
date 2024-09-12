package com.example.weather_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.controllers.WeatherController
import com.example.weather_app.databinding.ActivityLocationBinding


class LocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {




            btnEnter.setOnClickListener {

                val location = enterLocation.text.toString()

                if (location.isEmpty()) {

                    Log.e("LocationIsEmpty", "True")

                    Toast.makeText(
                        this@LocationActivity,
                        "Введите локацию\nПоле ввода пустое",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    when(isValidLocation(this@LocationActivity, location)) {
                        true -> with(this@LocationActivity.getSharedPreferences(getString(R.string.currCity), MODE_PRIVATE).edit()) {

                            Log.e("ValidLocation", "True and going update currCity")

                            putString(getString(R.string.currCity), location)

                            commit()
                        }

                        else -> {

                            Log.e("ValidLocation", "False")

                            Toast.makeText(
                                this@LocationActivity,
                                "Такая локация не найдена",
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            btnBack.setOnClickListener {
                val intent = Intent(this@LocationActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

fun isValidLocation(context: Context, location: String): Boolean {
    val geocoder = Geocoder(context)
    return geocoder.getFromLocationName(location, 1)!!.size > 0
}
