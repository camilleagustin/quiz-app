package com.example.quizapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.helpers.QuizDbHelper;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);

        Button btnRegister = findViewById(R.id.btnRegisterAccount);
        EditText editUsername = findViewById(R.id.usernameRegister); 
        EditText editName = findViewById(R.id.displayName);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = editUsername.getText().toString();
                String name = editName.getText().toString();

                if (usernameInput.equals("") || name.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUser = dbHelper.checkUsername(usernameInput);
                    if (checkUser == true) {
                        Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();

                    } else {
                        boolean insertUser = dbHelper.insertUser(usernameInput, name, 0, 0);
                        if (insertUser == true) {
                            editor.putString(Constants.KEY_USERNAME, usernameInput);
                            editor.putString(Constants.KEY_NAME, name);
                            editor.putInt(Constants.KEY_ISDARKMODE, 0);
                            editor.apply();
                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
}