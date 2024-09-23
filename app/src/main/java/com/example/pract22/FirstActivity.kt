package com.example.pract22

import android.app.DownloadManager.Request
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.compose.material3.Snackbar
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject

class FirstActivity : AppCompatActivity() {
    lateinit var city1: EditText

    lateinit var city_wr: TextView
    lateinit var temp_wr: TextView
    lateinit var air_wr: TextView
    lateinit var speed_wr: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
    }

    fun search_Click(view: View) {
        city1 = findViewById(R.id.users_city)

        city_wr = findViewById(R.id.city_written)
        temp_wr = findViewById(R.id.temp_written)
        air_wr = findViewById(R.id.airPres_written)
        speed_wr = findViewById(R.id.windSpeed_written)

        city_wr.text = " "
        temp_wr.text = " "
        air_wr.text = " "
        speed_wr.text = " "

        if (city1.text.toString().isNotEmpty()) {
            var key = "6ecd2b8f7c4faf4e80efe6296b01e089"
            var url =
                "https://api.openweathermap.org/data/2.5/weather?q=" + city1.text.toString() + "&appid=" + key + "&units=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                { response ->
                    val obj = JSONObject(response)

                    val cityName = obj.getString("name")
                    city_wr.text = cityName

                    val tempOBJ = obj.getJSONObject("main")
                    val temp = tempOBJ.getDouble("temp")
                    temp_wr.text = "$temp °C"

                    val airOBJ = obj.getJSONObject("main")
                    val pressure = airOBJ.getDouble("pressure")
                    air_wr.text = pressure.toString()

                    val windObject = obj.getJSONObject("wind")
                    val windSpeed = windObject.getDouble("speed")
                    speed_wr.text = "$windSpeed м/с"

                },
                {
                    var sn= Snackbar.make(findViewById(android.R.id.content),"Город не найден. Проверьте запрос.",Snackbar.LENGTH_LONG)
                    sn.setActionTextColor(Color.RED)
                    sn.show()

                }
            )
            queue.add(stringRequest)
        } else {
            Snackbar.make(view, "Введите город", Snackbar.LENGTH_LONG).show()
        }
    }
}