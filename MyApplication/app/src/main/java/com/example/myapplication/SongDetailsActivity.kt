package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongDetailsActivity : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var toggleButton: Button
    private lateinit var changeSpeedButton: Button
    private lateinit var currentSpeedTextView: TextView
    private var isAutoScrolling = false
    private val handler = Handler()

    private var scrollSpeed = 1
    private var delay = 50L
    private var initialDelay = 7500L

    private val runnable = object : Runnable {
        override fun run() {
            if (isAutoScrolling) {
                scrollView.smoothScrollBy(0, scrollSpeed)
                handler.postDelayed(this, delay)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_details)

        val title = intent.getStringExtra("title")
        val artist = intent.getStringExtra("artist")
        val fullText = intent.getStringExtra("fullText")
        val chords = intent.getStringExtra("chords")
        val battle = intent.getStringExtra("battle")

        findViewById<TextView>(R.id.detailsTitle).text = title
        findViewById<TextView>(R.id.detailsArtist).text = artist
        findViewById<TextView>(R.id.detailsFullText).text = fullText
        findViewById<TextView>(R.id.detailsChords).text = chords
        findViewById<TextView>(R.id.detailsBattle).text = battle

        scrollView = findViewById(R.id.detailsScrollView)
        toggleButton = findViewById(R.id.toggleAutoScrollButton)
        changeSpeedButton = findViewById(R.id.changeSpeedButton)
        currentSpeedTextView = findViewById(R.id.currentSpeedTextView)
        toggleButton.setOnClickListener {
            toggleAutoScroll()
        }
        changeSpeedButton.setOnClickListener {
            changeScrollSpeed()
        }
    }

    private fun toggleAutoScroll() {
        isAutoScrolling = !isAutoScrolling
        if (isAutoScrolling) {
            handler.postDelayed(runnable, initialDelay) // Добавляем задержку в начале
            toggleButton.text = "Остановить прокрутку"
        } else {
            handler.removeCallbacks(runnable)
            toggleButton.text = "Начать прокрутку"
        }
    }

    private var currentSpeedIndex = 0
    private val speeds = listOf(
        1 to 225L, // Медленная скорость
        2 to 200L, // Средняя скорость
        3 to 175L, // Быстрая скорость
        4 to 150L, // Очень быстрая скорость
        5 to 125L   // Сверхбыстрая скорость
    )

    private fun changeScrollSpeed() {
        val (speed, delay) = speeds[currentSpeedIndex]
        scrollSpeed = speed
        this.delay = delay
        currentSpeedIndex = (currentSpeedIndex + 1) % speeds.size
        currentSpeedTextView.text = "Скорость: $scrollSpeed"
        if (isAutoScrolling) {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, initialDelay) // Добавляем задержку в начале
        }
    }
}