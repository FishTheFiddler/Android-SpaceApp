package com.example.ourfinalfrontier

import android.app.Activity
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NasaHttpTask() : Runnable{

    var returnText : String = ""
    val apiKey : String = "5M1iIO9Q7pMJQfG9YpJ5bAJ9foVVbcD83YwaTmza";
    val nasaURL : String = "https://api.nasa.gov/insight_weather/?api_key=$apiKey&feedtype=json&ver=1.0"
    //var activity : Activity = TODO()

    /*fun NasaHttpTask(activity : Activity){
        this.activity = activity
    }*/


    fun getHttpContent(string: String): String{
        var content = ""
        try {
            val url = URL(string)
            val http: HttpURLConnection = url.openConnection() as HttpURLConnection
            val reader = BufferedReader(InputStreamReader(http.getInputStream()))
            val stringBuilder = StringBuilder()
            var line: String
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(
                    """
                    $line
                    """.trimIndent()
                )
            }
            content = stringBuilder.toString()
        } catch (e: Exception) {
            System.err.println(e.toString())
        }
        return content
    }

    public override fun run() {
        //returnText = getHttpContent(nasaURL)
        Thread.sleep(1000)
        Log.d("Return", "SUCCESS")
    }

}