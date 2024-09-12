package com.example.weather_app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.databinding.ActivityLocationBinding


class LocationActivity : AppCompatActivity() {

    private var binding = ActivityLocationBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnBack.setOnClickListener {
                val intent = Intent(this@LocationActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}