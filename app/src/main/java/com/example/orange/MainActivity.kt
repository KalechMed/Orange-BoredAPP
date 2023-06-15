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
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.cardview.widget.CardView

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
    private lateinit var cardView: CardView
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_cancel: ImageView = findViewById(R.id.cancel)
        val btn_like: ImageView = findViewById(R.id.like)
        val btn_star: ImageView = findViewById(R.id.star)

        val  text_Activity: TextView = findViewById(R.id.activity)
        cardView = findViewById(R.id.view2)

        gestureDetector = GestureDetector(this, SwipeGestureListener())

        cardView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

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

    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val swipeThreshold = 100
            val swipeVelocityThreshold = 100

            if (e1 != null && e2 != null) {
                val deltaX = e2.x - e1.x
                val deltaY = e2.y - e1.y

                if (kotlin.math.abs(deltaX) > kotlin.math.abs(deltaY) &&
                    kotlin.math.abs(deltaX) > swipeThreshold &&
                    kotlin.math.abs(velocityX) > swipeVelocityThreshold
                ) {
                    if (deltaX > 0) {
                        onLike()
                    } else {

                        onCancel()
                    }
                    return true
                }
            }

            return false
        }
    }

    private fun onLike() {
        val cardView = findViewById<CardView>(R.id.view2)
        val  text_Activity: TextView = findViewById(R.id.activity)


        cardView.animate()
            .translationXBy(cardView.width.toFloat())
            .rotation(30f)
            .alpha(0f)
            .setDuration(300)
            .withEndAction {

                cardView.translationX = 0f
                cardView.rotation = 0f
                cardView.alpha = 1f
            }
            .start()

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

    private fun onCancel() {
        val  text_Activity: TextView = findViewById(R.id.activity)
        val cardView = findViewById<CardView>(R.id.view2)


        cardView.animate()
            .translationXBy(-cardView.width.toFloat())
            .rotation(-30f)
            .alpha(0f)
            .setDuration(300)
            .withEndAction {

                cardView.translationX = 0f
                cardView.rotation = 0f
                cardView.alpha = 1f
            }
            .start()

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
}







