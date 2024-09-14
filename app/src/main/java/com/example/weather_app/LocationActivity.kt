package com.example.weather_app

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.adapters.RecyclerViewAdapter
import com.example.weather_app.databinding.ActivityLocationBinding
import com.example.weather_app.models.CityViewModel


class LocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            val sharedPref = this@LocationActivity.getSharedPreferences(getString(R.string.prefData), MODE_PRIVATE)

            updateListView(
                sharedPref.getStringSet(
                    getString(R.string.listCity),
                    arrayOf(sharedPref.getString(
                        getString(R.string.saveCity),
                        getString(R.string.saveCity)
                    )).toSet()
                )!!.toSet()
            )

            Log.e(
                "CreateLocation",
                "currCity: ${sharedPref.getString(getString(R.string.currCity), null)}; saveCity: ${sharedPref.getString(getString(R.string.saveCity), null)}"
            )

            btnEnter.setOnClickListener {

                var location = enterLocation.text.toString()

                if (location.isEmpty()) {

                    Log.e("LocationIsEmpty", "True")

                    Toast.makeText(
                        this@LocationActivity,
                        "Введите локацию\nПоле ввода пустое",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    val resValidLoc = isValidLocation(this@LocationActivity, location)
                    val isValidLoc = resValidLoc.first

                    when(isValidLoc) {
                         true -> {
                            with(this@LocationActivity.getSharedPreferences(getString(R.string.prefData), MODE_PRIVATE)) {

                                location = resValidLoc.second!!

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

                                updateListView(setStr)
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

    private fun updateListView(setString: Set<String>) {
        val listCity = ArrayList<CityViewModel>()

        binding = ActivityLocationBinding.inflate(layoutInflater)

        with(binding) {
            listCities.layoutManager = LinearLayoutManager(this@LocationActivity)

            for (str in setString) {
                listCity.add(CityViewModel(str))

                Log.e("ListCity", str)
            }

            listCities.adapter = RecyclerViewAdapter(this@LocationActivity, listCity)
        }

        Log.e("UpdateListView", "Updating is done, ${binding.listCities.adapter!!.itemCount} items was add")
        Log.e("АДАПТЕР", binding.listCities.adapter.toString())
    }
}

fun isValidLocation(context: Context, location: String): Pair<Boolean, String?> {
    val geocoder = Geocoder(context)
    val address = geocoder.getFromLocationName(location, 1)

    return if (address!!.size > 0) Pair(true, address[0].featureName) else Pair(false, null)
}
