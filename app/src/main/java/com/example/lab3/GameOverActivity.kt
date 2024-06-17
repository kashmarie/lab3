package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {
    private lateinit var startGameAgain: Button
    private lateinit var DisplayScore: TextView
    private lateinit var score: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        score = intent.getStringExtra("score").toString()

        startGameAgain = findViewById(R.id.play_again)
        DisplayScore = findViewById(R.id.displayScore)

        startGameAgain.setOnClickListener {
            val mainIntent = Intent(this@GameOverActivity, MainActivity::class.java)
            startActivity(mainIntent)
        }

        DisplayScore.text = getString(R.string.score1, score)

    }
}
