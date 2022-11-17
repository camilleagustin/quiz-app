package com.example.quizapp.models;

public class Trivia {
    private int id;
    private String trivia;

    public Trivia() {}

    public Trivia(String trivia) {
        this.trivia = trivia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrivia() {
        return trivia;
    }

    public void setTrivia(String trivia) {
        this.trivia = trivia;
    }
}
