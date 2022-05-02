package com.example.ourfinalfrontier

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun trackerHub (view: View){
        Log.d("Intent", "Creating Intent to TrackerHub.kt")
        val intent = Intent(this, IssTracker::class.java)
        Log.d("Intent", "Intent defined")
        startActivity(intent)
    }

    fun dailyImage (view: View){
        Log.d("Intent", "Creating Intent to DailyImage.kt")
        val intent = Intent(this, DailyImage::class.java)
        Log.d("Intent", "Intent defined")
        startActivity(intent)
    }

    fun Display(output: String?) {
        Log.d("JSON", output.toString())
    }
}