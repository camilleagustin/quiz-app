package com.example.quizapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Bookmark implements Parcelable {
    private String username;
    private int categoryId;
    private String difficulty;
    private int questionId;
    private String question;
    private String answer;

    public Bookmark() {
    }

    public Bookmark(String username, int categoryId, String difficulty, int questionId, String question, String answer) {
        this.username = username;
        this.categoryId = categoryId;
        this.difficulty = difficulty;
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
    }

    protected Bookmark(Parcel in) {
        username = in.readString();
        categoryId = in.readInt();
        difficulty = in.readString();
        questionId = in.readInt();
        question = in.readString();
        answer = in.readString();
    }

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(categoryId);
        dest.writeString(difficulty);
        dest.writeInt(questionId);
        dest.writeString(question);
        dest.writeString(answer);
    }
}
