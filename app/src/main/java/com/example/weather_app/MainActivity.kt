package com.example.weather_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var enterLocation : EditText
    private lateinit var weatherLocation : TextView
    private lateinit var btnEnter : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        enterLocation = findViewById(R.id.enterLocation)
        weatherLocation = findViewById(R.id.weatherLocation)
        btnEnter = findViewById(R.id.btnEnter)


    }
}