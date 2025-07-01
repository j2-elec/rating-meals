package com.example.ratingmeals

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SuccessScreen : AppCompatActivity() {
    private val autoCloseDelay: Long = 5000 // 5 seconds
    private val handler = Handler(Looper.getMainLooper())
    private val closeRunnable = Runnable { finish() }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success_screen)

        // Set up close button
        val closeButton = findViewById<Button>(R.id.buttonBack_Success)
        closeButton.setOnClickListener {
            handler.removeCallbacks(closeRunnable)
            finish()
        }

        // Schedule auto-close after 5 seconds
        handler.postDelayed(closeRunnable, autoCloseDelay)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(closeRunnable)
    }
}