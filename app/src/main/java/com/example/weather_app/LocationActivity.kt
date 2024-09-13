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

            Log.e("CreateLocation", "currCity: ${getString(R.string.currCity)}; saveCity: ${getString(R.string.saveCity)}")

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
                        true -> {
                            with(this@LocationActivity.getSharedPreferences(getString(R.string.prefData), MODE_PRIVATE)) {

                                Log.e("ValidLocation", "True and going update currCity")

                                with(edit()) {
                                    putString(getString(R.string.currCity), location)
                                    apply()
                                }

                                Log.e("CurrCity", "CurrCity is ${getString(getString(R.string.currCity), null)}")

                                if (getString(getString(R.string.saveCity), null) == null) {
                                    with(edit()) {
                                        Log.e("SaveCity", "SaveCity is null, starting putString for saveCity")
                                        putString(getString(R.string.saveCity), location)
                                        apply()
                                    }
                                }

                                Log.e("SaveCity", "SaveCity is ${getString(getString(R.string.saveCity), null)}")
                            }

                            with(this@LocationActivity.getSharedPreferences(getString(R.string.prefData), MODE_PRIVATE)) {

                                val saveCity = getString(getString(R.string.saveCity), null)

                                val listCity = getStringSet(getString(R.string.listCity), arrayOf(saveCity).toSet())!!
                                    .plus(saveCity).plus(location)

                                with(edit()){
                                    putStringSet(
                                        getString(R.string.listCity),
                                        listCity
                                    )

                                    apply()
                                }

                                val setStr = getStringSet(getString(R.string.listCity), null)!!

                                Log.e("StringSet", setStr.toString())
                                Log.e("StringSet", setStr.size.toString())
                            }

                            Toast.makeText(this@LocationActivity, "Погода обновлена", Toast.LENGTH_SHORT).show()
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
