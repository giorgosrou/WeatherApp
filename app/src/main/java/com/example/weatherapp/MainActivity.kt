package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val city: String = "London"
    val API: String = "1497dd20e8bdb334b5a1f8a77050bc76"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.executeAsyncTask(onPreExecute = {
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }, doInBackground = {
            var response: String?
            try{
            response = URL("https://api.openweathermap.org/data/2.5/weather?q=city&units=metric&appid=API").readText(Charsets.UTF_8)
            }
            catch (e:Exception) {
                response = null
            }
        }, onPostExecute = {
            try {
                val jsonObj = JSONObject()
                //val main = jsonObj.getJSONObject("main")
                //val sys = jsonObj.getJSONObject("sys")
                //val wind = jsonObj.getJSONObject("wind")
                //val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
            }
            catch (e: java.lang.Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        })

    }

    // extension function:
    fun <R> CoroutineScope.executeAsyncTask(
        onPreExecute: () -> Unit,
        doInBackground: () -> R,
        onPostExecute: (R) -> Unit
    ) = launch {
        onPreExecute()
        val result = withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
            doInBackground()
        }
        onPostExecute(result)
    }
}