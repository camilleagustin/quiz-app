package com.example.quizapp.models;

public class Score {
    private String name;
    private int categoryId;
    private String difficulty;
    private int score;
    private int avatar;

    public Score() {
    }

    public Score(String name, int categoryId, String difficulty, int score, int avatar) {
        this.name = name;
        this.categoryId = categoryId;
        this.difficulty = difficulty;
        this.score = score;
        this.avatar = avatar;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
