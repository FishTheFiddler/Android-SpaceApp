package com.example.ourfinalfrontier

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Executors


class DailyImage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_image)
        Log.d("Intent", "Intent Received")

        ObtainInfo()
    }

        fun ObtainInfo() {
            var apiKey: String = "DEMO_KEY";
            val nasaURL: String =
                "https://api.nasa.gov/planetary/apod?api_key=$apiKey&thumbs=True" //&feedtype=json&ver=1.0"
            var output: String = ""
            GlobalScope.launch {
                output = getHttpContent(nasaURL)
                var obj = JSONObject(output)
                var date = obj.getString("date")
                var explanation = obj.getString("explanation")
                var title = obj.getString("title")
                var URL = obj.getString("url")
                var image = TodaysImage(date, explanation, title, URL)
                DisplayResults(image)
            }
        }


        suspend fun getHttpContent(string: String): String {
            var content = ""

            try {
                val url = URL(string)
                val connection = url.openConnection()
                var sb: StringBuilder = StringBuilder()
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

    fun LoadImage(imageView: ImageView, url : String){
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {
            val imageURL = url
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    imageView.setImageBitmap(image)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


        fun DisplayResults(image: TodaysImage) {

            val date : TextView = findViewById(R.id.date)
            val explanation : TextView = findViewById(R.id.imageDescription)
            val imageView : ImageView = findViewById<ImageView>(R.id.image)
            val imageTitle : TextView = findViewById(R.id.title)

            LoadImage(imageView, image.URL)
            date.setText("Today's Date: " + image.date)
            explanation.setText(image.explanation)
            imageTitle.setText(image.title)
        }
    }






