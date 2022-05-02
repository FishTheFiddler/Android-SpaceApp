package com.example.ourfinalfrontier

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import org.json.JSONObject
import java.util.*


class IssTracker : AppCompatActivity() {
    var tracking = false
    var canTrack = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.iss_tracker)
        Log.d("Intent", "Intent Received")

        var issSymbol : ImageView = findViewById(R.id.issSymbol)
        issSymbol.isVisible = false
    }

    fun StartTracking(view: View){
        var issSymbol : ImageView = findViewById(R.id.issSymbol)
        val issURL : String = "http://api.open-notify.org/iss-now.json"
        var output : String = ""
        issSymbol.isVisible = true
        if (canTrack){
            tracking = true
            canTrack = false
            Log.d("ISS_TRACKER", "Tracking Started")
            GlobalScope.launch {
                while (tracking) {
                    output = getHttpContent(issURL)
                    var obj = JSONObject(output)
                    var location = JSONObject()
                    var message = obj.getString("message")
                    var timestamp = obj.getInt("timestamp")
                    var locationStr = obj.getString("iss_position")
                    location = JSONObject(locationStr)
                    var latitude = location.getDouble("latitude")
                    var longitude = location.getDouble("longitude")
                    var issPosition = IssPosition(message, latitude, longitude, timestamp)

                    DisplayResults(issPosition)
                    MoveISS(issPosition)
                    delay(1000)
                    }
                }
            }
        else{
            Log.d("ISS_TRACKER", "Already Tracking")
        }
    }

    suspend fun getHttpContent(string: String): String{
        var content = ""

        try {
            val url = URL(string)
            val connection = url.openConnection()
            var sb : StringBuilder = StringBuilder()
            BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
                var line: String?
                while (inp.readLine().also { line = it } != null) {
                    sb.append(line + "\n")
                    content = sb.toString()
                }
            }
        } catch (e: Exception) {
            System.err.println(e.toString())
        }
        return content
    }

    fun StopTracking(view: View){
        var issSymbol : ImageView = findViewById(R.id.issSymbol)
        tracking = false
        canTrack = true
        issSymbol.isVisible = false
        Log.d("ISS_TRACKER", "Tracking Stopped")
        var output : TextView = findViewById(R.id.output)
        output.setText("Press START to begin tracking.")
    }

    fun MoveISS(issPosition: IssPosition){
        var issSymbol : ImageView = findViewById(R.id.issSymbol)
        var worldMap : ImageView = findViewById(R.id.worldMap)

        var leftBound : Float = (worldMap.x - 60)
        var rightBound : Float = ((worldMap.x + worldMap.width) - 60)
        var topBound : Float = (worldMap.y - 40)
        var bottomBound : Float = ((worldMap.y + worldMap.height) - 70)

        var boundaryWidth : Float = rightBound - leftBound
        var boundaryHeight : Float = bottomBound - topBound

        var latitude : Float = issPosition.latitude.toFloat()
        var longitude : Float = issPosition.longitude.toFloat()

        //issSymbol.isVisible = true
        issSymbol.translationY = ((bottomBound - ((latitude + 90) / (180.0 / boundaryHeight))).toFloat())
        issSymbol.translationX = ((leftBound + ((longitude + 180) / (360.0 / boundaryWidth))).toFloat())

    }

    fun DisplayResults(issPosition: IssPosition){
        var output : TextView = findViewById(R.id.output)
        output.setText("Current location of the ISS: " +
                       "\n\nLatitude: " + issPosition.latitude +
                       "\nLongitude: " + issPosition.longitude +
                       "\nTimestamp: " + issPosition.timestamp)
    }

}


