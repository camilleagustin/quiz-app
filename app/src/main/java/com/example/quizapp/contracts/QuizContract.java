package com.example.quizapp.contracts;

import android.provider.BaseColumns;

public final class QuizContract {
    private QuizContract() {
    }

    public static class CategoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_categories";
        public static final String COLUMN_NAME = "name";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER = "correct_answer";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }

    public static class TriviaTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_trivia";
        public static final String COLUMN_TRIVIA = "trivia";
    }

    public static class UsersTable {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_DISPLAY_NAME = "display_name";
        public static final String COLUMN_AVATAR = "image_number";
        public static final String COLUMN_DARKMODE_PREF = "darkmode_pref";
    }

    public static final class BookmarksTable {
        public static final String TABLE_NAME = "bookmarks";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";
    }

    public static final class HistoryTable {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_SCORE = "score";
    }

    public static final class ScoreTable {
        public static final String TABLE_NAME = "top_scores";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_SCORE = "score";
    }

}
