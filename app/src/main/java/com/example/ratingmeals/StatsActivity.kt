package com.example.ratingmeals

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stats)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish() // returns to MainActivity
        }
        val resetButton = findViewById<Button>(R.id.resetButton);
        val avgScore = findViewById<TextView>(R.id.avgScore);
        val filename = "canteen_stats.txt"
        val statsList = mutableListOf<Pair<Int, String>>()

        try {
            openFileInput(filename).bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val parts = line.split("|")
                    if (parts.size >= 1) {
                        val score = parts[0].toIntOrNull() ?: 0
                        val suggestion = if (parts.size > 1) parts[1] else ""
                        statsList.add(Pair(score, suggestion))
                    }
                }
            }
        } catch (e: Exception) {
            // File may not exist yet, handle gracefully
        }

        val average = if (statsList.isNotEmpty()) {
            statsList.map { it.first }.average()
        } else {
            0.0
        }

        avgScore.text = "Average score: %.2f".format(average)

// Parse lines to calculate average and display suggestions
        resetButton.setOnClickListener {
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write("".toByteArray())
            }
            // Clear UI
            avgScore.text = "Average score: 0.00"
            val emptyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOf<String>())
            //suggestionsListView.adapter = emptyAdapter
        }
    }
}