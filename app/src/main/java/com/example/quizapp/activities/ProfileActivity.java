package com.example.quizapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.quizapp.R;
import com.example.quizapp.adapters.AvatarAdapter;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.models.User;

public class ProfileActivity extends AppCompatActivity {
    private String name;
    private String newName;
    private String username;
    private int avatar;
    private int newAvatar;
    private QuizDbHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        GridView grid = findViewById(R.id.grid_avatars);
        Button btnSave = findViewById(R.id.btnSave);
        SwitchCompat switchDark = findViewById(R.id.switchDarkMode);
        editName = findViewById(R.id.editName);

        sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);
        dbHelper = QuizDbHelper.getInstance(this);

        username = sharedPreferences.getString(Constants.KEY_USERNAME, "DefaultValue");
        name = sharedPreferences.getString(Constants.KEY_NAME, "DefaultValue");

        User user = dbHelper.getUser(username);
        avatar = user.getAvatar();

        editName.setText(name);

        AvatarAdapter adapter = new AvatarAdapter(ProfileActivity.this, DashboardActivity.images);
        grid.setAdapter(adapter);
        adapter.setSelectedPosition(avatar);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
                newAvatar = position;

            }
        });

        int darkModePref = sharedPreferences.getInt(Constants.KEY_ISDARKMODE, 0);

        if (darkModePref == 0) {
            switchDark.setChecked(false);
        } else {
            switchDark.setChecked(true);
        }

        switchDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(1);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(0);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void saveNightModeState(int isDarkMode) {
        dbHelper.updateDarkModePref(username, isDarkMode);
        editor = sharedPreferences.edit();
        editor.putInt(Constants.KEY_ISDARKMODE, isDarkMode);
        editor.apply();
    }

    private void saveChanges() {
        editor = sharedPreferences.edit();
        newName = editName.getText().toString();

        //checks if name was changed
        if (!name.equals(newName)) {
            dbHelper.updateName(username, newName);
            editor.putString(Constants.KEY_NAME, newName);
        }

        //checks if avatar was changed
        if (!(avatar == newAvatar)) {
            dbHelper.updateAvatar(username, newAvatar);
        }
        editor.apply();
        Toast.makeText(ProfileActivity.this, "Changes Saved!", Toast.LENGTH_SHORT).show();
        finish();

    }
}