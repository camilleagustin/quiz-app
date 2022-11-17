package com.example.quizapp.models;

public class User {
    private String username;
    private String displayName;
    private int avatar;
    private int darkModePref;

    public User() {
    }

    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = displayName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getDarkModePref() {
        return darkModePref;
    }

    public void setDarkModePref(int darkModePref) {
        this.darkModePref = darkModePref;
    }
}
