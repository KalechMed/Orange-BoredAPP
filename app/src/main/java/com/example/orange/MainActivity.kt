package com.example.orange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testtt.ApiClient.apiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import android.content.SharedPreferences

fun saveArrayList(context: Context, key: String, arrayList: ArrayList<String>) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(arrayList)
    editor.putString(key, json)
    editor.apply()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_cancel: ImageView = findViewById(R.id.cancel)
        val btn_like: ImageView = findViewById(R.id.like)
        val btn_star: ImageView = findViewById(R.id.star)

        val  text_Activity: TextView = findViewById(R.id.activity)

        CoroutineScope(Dispatchers.IO).launch {

                val response = apiService.getActivity()
                val activity = response.activity

                runOnUiThread {

                    text_Activity.text = activity




                }

        }


        btn_cancel.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getActivity()
                    val activity = response.activity

                    runOnUiThread {

                        text_Activity.text = activity
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }



        btn_like.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getActivity()
                    val activity = response.activity


                    val likedActivities = getArrayList(applicationContext, "likedActivitiesKey")
                    likedActivities.add(text_Activity.text as String)
                    saveArrayList(applicationContext, "likedActivitiesKey", likedActivities)

                    runOnUiThread {
                        text_Activity.text = activity
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }




        btn_star.setOnClickListener {
            val intent = Intent(this@MainActivity, favorites::class.java)
            startActivity(intent)
        }









    }


}



