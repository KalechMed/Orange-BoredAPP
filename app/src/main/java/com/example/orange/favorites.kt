package com.example.orange

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtt.Adapter

fun getArrayList(context: Context, key: String): ArrayList<String> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString(key, null)
    val type = object : TypeToken<ArrayList<String>>() {}.type
    return gson.fromJson(json, type) ?: ArrayList()
}


class favorites : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrievedArrayList = getArrayList(applicationContext, "likedActivitiesKey")
        val adapter = Adapter(retrievedArrayList)
        recyclerView.adapter = adapter

        val buttonBack: ImageView = findViewById(R.id.back)

        buttonBack.setOnClickListener {
            val intent = Intent(this@favorites, MainActivity::class.java)
            startActivity(intent)
        }




    }
}