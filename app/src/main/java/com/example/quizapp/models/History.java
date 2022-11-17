package com.example.quizapp.models;

public class History {
    private String username;
    private int categoryId;
    private String difficulty;
    private int score;

    public History() {
    }

    public History(String username, int categoryId, String difficulty, int score) {
        this.username = username;
        this.categoryId = categoryId;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
