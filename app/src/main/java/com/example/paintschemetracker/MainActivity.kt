package com.example.paintschemetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//This is the main activity, for this app, its sole purpose is to go to our activity main xml file, and launch the nav graph
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}