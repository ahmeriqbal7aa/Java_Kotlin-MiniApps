package com.example.justmathsjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    TextView txtViewQuestion, txtViewResult, txtTime, txtScore, txtRightWrong;
    ImageButton btnCorrect, btnIncorrect;
    boolean isResultCorrect;
    int seconds = 10;
    private int score = 0;
    private boolean stopTimer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtViewQuestion = findViewById(R.id.tvQuestion);
        txtViewResult = findViewById(R.id.tvResult);
        txtTime = findViewById(R.id.txtTime);
        txtScore = findViewById(R.id.txtScore);
        txtRightWrong = findViewById(R.id.tvRightWrong);
        btnCorrect = findViewById(R.id.btnRight);
        btnIncorrect = findViewById(R.id.btnWrong);

        timer();

        btnCorrect.setOnClickListener(v -> verifyAnswer(true));
        btnIncorrect.setOnClickListener(v -> verifyAnswer(false));

        generateQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void generateQuestion() {
        isResultCorrect = true;
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        int result = a + b;
        float f = random.nextFloat(); // generated between 0 and 1
        if (f > 0.5f) {
            result = random.nextInt(100);
            isResultCorrect = false;
        }
        txtViewQuestion.setText(a + "+" + b);
        txtViewResult.setText("=" + result);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void verifyAnswer(boolean answer) {
        if (answer == isResultCorrect) {
            score += 5;
            txtScore.setText("SCORE: " + score);
            txtRightWrong.setVisibility(View.VISIBLE);
            txtRightWrong.setText("True");
            txtRightWrong.setTextColor(getResources().getColor(R.color.green));
        } else {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            //noinspection deprecation
            vibrator.vibrate(100);
            txtRightWrong.setVisibility(View.VISIBLE);
            txtRightWrong.setText("False");
            txtRightWrong.setTextColor(getResources().getColor(R.color.red));
        }
        generateQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                txtTime.setText("TIME :" + seconds);
                seconds--;
                if (seconds < 0) {
                    Intent i = new Intent(GameActivity.this, ScoreActivity.class);
                    i.putExtra("score", score);
                    startActivity(i);
                    stopTimer = true;
                }
                if (!stopTimer) {
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer = false;
        finish();
    }
}