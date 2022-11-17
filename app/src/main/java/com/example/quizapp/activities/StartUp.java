package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.helpers.QuizDbHelper;

public class StartUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);

        try {
            dbHelper.createDatabase();
            dbHelper.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(StartUp.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        }, 3000);
    }
}
