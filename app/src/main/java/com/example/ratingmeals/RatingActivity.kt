package com.example.ratingmeals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RatingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rating)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val statsButton = findViewById<Button>(R.id.statsActivityButton)
        statsButton.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val suggestionEditText = findViewById<EditText>(R.id.sugestionText)
        val submitButton = findViewById<Button>(R.id.submitButton);
        submitButton.setOnClickListener {
            val intent = Intent(this, SuccessScreen::class.java)

            val score = ratingBar.rating.toInt()
            val suggestion = suggestionEditText.text.toString()

            val filename = "canteen_stats.txt"
            val fileContents = "$score|$suggestion\n"
            openFileOutput(filename, Context.MODE_APPEND).use {
                it.write(fileContents.toByteArray())
            }
            Handler(Looper.getMainLooper()).postDelayed({
                // Reset RatingBar and EditText
                ratingBar.rating = 3f;
                suggestionEditText.text.clear()
            }, 1000)
            startActivity(intent)
//            // Show a Toast message
//            val toast = Toast.makeText(this, "Raspuns salvat!", Toast.LENGTH_LONG)
//            toast.show()
//
//            // Handler to delay reset by 3 seconds (3000 ms)
//            Handler(Looper.getMainLooper()).postDelayed({
//                // Reset RatingBar and EditText
//                ratingBar.rating = 0f
//                suggestionEditText.text.clear()
//                toast.cancel() // Hide the toast if it's still visible
//            }, 5000)
        }
    }


}

