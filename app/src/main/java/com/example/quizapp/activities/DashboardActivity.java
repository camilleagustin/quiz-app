package com.example.quizapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.quizapp.R;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.dialogs.TriviaDialog;
import com.example.quizapp.fragments.BookmarkFragment;
import com.example.quizapp.fragments.HighScoreFragment;
import com.example.quizapp.fragments.HistoryFragment;
import com.example.quizapp.fragments.HomeFragment;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.models.Trivia;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    public static int[] images = {
            R.drawable.avatar_one, R.drawable.avatar_two,
            R.drawable.avatar_three, R.drawable.avatar_four,
            R.drawable.avatar_five, R.drawable.avatar_six,
            R.drawable.avatar_seven, R.drawable.avatar_eight};

    public static List<Trivia> triviaList;
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        triviaList = dbHelper.getAllTrivia();
        sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);

        checkDarkMode();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //sets the default fragment to be displayed
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        //sets the default item that is selected
        bottomNav.setSelectedItemId(R.id.homeFragment);
    }

    //sets up a listener to check what menu item is clicked on
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    boolean select = true;

                    //checks the id of the selected menu item and creates the instance of the fragment
                    switch (item.getItemId()) {

                        case R.id.homeFragment:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.historyFragment:
                            selectedFragment = new HistoryFragment();
                            break;
                        case R.id.bookmarkFragment:
                            selectedFragment = new BookmarkFragment();
                            break;
                        case R.id.highscoreFragment:
                            selectedFragment = new HighScoreFragment();
                            break;
                        case R.id.triviaDialog:
                            openTrivia();
                            break;
                    }
                    /* shows the fragment of the selected menu item and prevents triviaDialog
                    from trying to open a non existent fragment*/
                    if (item.getItemId() != R.id.triviaDialog) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    } else {
                        select = false;
                    }
                    return select;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                openAbout();
                break;
            case R.id.avatar:
                openProfile();
                break;
            case R.id.logout:
                logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openTrivia() {
        TriviaDialog trivia = new TriviaDialog();
        trivia.show(getSupportFragmentManager(), "trivia dialog");
    }

    private void openAbout() {
        Intent intent = new Intent(DashboardActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        finishAffinity();
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
    }

    public static void checkDarkMode() {
        int x = sharedPreferences.getInt(Constants.KEY_ISDARKMODE, 1);
        if (x == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}
