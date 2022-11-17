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
import com.example.quizapp.models.User;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        EditText editUsername = findViewById(R.id.usernameLogin);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);

        //checks if a user is still logged in
        if (sharedPreferences.contains(Constants.KEY_USERNAME)) {
            openDashboard();
            DashboardActivity.checkDarkMode();
        }
        // checks the username if it exists in database
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUsername = dbHelper.checkUsername(username);
                    if (checkUsername == true) {

                        User user = dbHelper.getUser(username);

                        String name = user.getDisplayName();
                        int darkMode = user.getDarkModePref();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.KEY_USERNAME, username);
                        editor.putString(Constants.KEY_NAME, name);
                        editor.putInt(Constants.KEY_ISDARKMODE, darkMode);
                        editor.apply();

                        openDashboard();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //opens register activity
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openDashboard() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}