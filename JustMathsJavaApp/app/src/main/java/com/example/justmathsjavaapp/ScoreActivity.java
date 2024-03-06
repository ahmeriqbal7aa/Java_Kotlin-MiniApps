package com.example.justmathsjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView textViewScore = findViewById(R.id.tvFinalScore);
        ImageButton btnScoreShare = findViewById(R.id.btnScoreShare);

        int score = getIntent().getIntExtra("score", 0);

        textViewScore.setText("Final Score : " + score);
        btnScoreShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Final Score: " + score + "\nJust Maths - Fun way to learn Maths. http://www.play.google.com");
            startActivity(intent);
        });
    }
}