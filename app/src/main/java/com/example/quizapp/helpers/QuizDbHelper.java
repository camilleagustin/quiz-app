package com.example.quizapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quizapp.contracts.QuizContract;
import com.example.quizapp.models.Bookmark;
import com.example.quizapp.models.Category;
import com.example.quizapp.models.History;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Score;
import com.example.quizapp.models.Trivia;
import com.example.quizapp.models.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizAppDB.db";
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH;
    private static QuizDbHelper instance;
    private final Context context;
    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).toString();
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            Log.e("Message: ", "" + e);
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream inputStream = context.getAssets().open("database/" + DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH;

        // Open the empty db as the output stream
        OutputStream outputStream = new FileOutputStream(outFileName);

        // transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        // Close the streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDataBase() throws SQLException {
        String path = DATABASE_PATH;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        // close the database.
        if (db != null)
            db.close();
        super.close();
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getColumnIndex(QuizContract.CategoriesTable._ID));
                category.setName(c.getString(c.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public Category getCategory(int categoryId) {
        Category category = new Category();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.CategoriesTable.TABLE_NAME + " WHERE " +
                QuizContract.CategoriesTable._ID + " = ? ", new String[]{String.valueOf(categoryId)});

        if (c.moveToFirst()) {
            do {
                category.setId(c.getInt(c.getColumnIndex(QuizContract.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
            } while (c.moveToNext());
        }
        return category;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<Question> getQuestions(int categoryId, String difficulty) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ? ";

        String[] selectionArgs = new String[]{String.valueOf(categoryId), difficulty};

        Cursor c = db.query(
                QuizContract.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<Trivia> getAllTrivia() {
        List<Trivia> triviaList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.TriviaTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Trivia trivia = new Trivia();
                trivia.setId(c.getColumnIndex(QuizContract.TriviaTable._ID));
                trivia.setTrivia(c.getString(c.getColumnIndex(QuizContract.TriviaTable.COLUMN_TRIVIA)));
                triviaList.add(trivia);
            } while (c.moveToNext());
        }

        c.close();
        return triviaList;
    }

    public boolean insertUser(String username, String name, int avatarId, int darModePref) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.UsersTable.COLUMN_USERNAME, username);
        cv.put(QuizContract.UsersTable.COLUMN_DISPLAY_NAME, name);
        cv.put(QuizContract.UsersTable.COLUMN_AVATAR, avatarId);
        cv.put(QuizContract.UsersTable.COLUMN_DARKMODE_PREF, darModePref);
        long result = db.insert(QuizContract.UsersTable.TABLE_NAME, null, cv);

        //returns true if inserted, otherwise returns false
        if (result != -1)
            return true;
        else
            return false;
    }

    public boolean checkUsername(String username) {
        db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.UsersTable.TABLE_NAME + " WHERE " +
                QuizContract.UsersTable.COLUMN_USERNAME + " = ? ", new String[]{username});

        //returns true if entry exists, otherwise returns false
        if (c.getCount() > 0)
            return true;
        else
            return false;

    }

    public User getUser(String username) {
        User user = new User();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.UsersTable.TABLE_NAME + " WHERE " +
                QuizContract.UsersTable.COLUMN_USERNAME + " = ? ", new String[]{username});
        if (c.moveToFirst()) {
            user.setUsername(c.getString(c.getColumnIndex(QuizContract.UsersTable.COLUMN_USERNAME)));
            user.setDisplayName(c.getString(c.getColumnIndex(QuizContract.UsersTable.COLUMN_DISPLAY_NAME)));
            user.setAvatar(c.getInt(c.getColumnIndex(QuizContract.UsersTable.COLUMN_AVATAR)));
            user.setDarkModePref(c.getInt(c.getColumnIndex(QuizContract.UsersTable.COLUMN_DARKMODE_PREF)));
        }

        return user;
    }

    public boolean updateName(String username, String name) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.UsersTable.COLUMN_DISPLAY_NAME, name);

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.UsersTable.TABLE_NAME + " WHERE " +
                QuizContract.UsersTable.COLUMN_USERNAME + " = ?", new String[]{username});

        if (c.getCount() > 0) {
            long result = db.update(
                    QuizContract.UsersTable.TABLE_NAME,
                    cv,
                    QuizContract.UsersTable.COLUMN_USERNAME + " = ?",
                    new String[]{username}
            );
            //returns true if updated, otherwise false
            if (result != -1)
                return true;
            else
                return false;
        } else
            return false;
    }

    public boolean updateAvatar(String username, int avatarId) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.UsersTable.COLUMN_AVATAR, avatarId);

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.UsersTable.TABLE_NAME + " WHERE " +
                QuizContract.UsersTable.COLUMN_USERNAME + " = ?", new String[]{username});

        if (c.getCount() > 0) {
            long result = db.update(
                    QuizContract.UsersTable.TABLE_NAME,
                    cv,
                    QuizContract.UsersTable.COLUMN_USERNAME + " = ?",
                    new String[]{username}
            );
            //returns true if updated, otherwise false
            if (result != -1)
                return true;
            else
                return false;
        } else
            return false;
    }

    public boolean updateDarkModePref(String username, int darkModePref) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.UsersTable.COLUMN_DARKMODE_PREF, darkModePref);

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.UsersTable.TABLE_NAME + " WHERE " +
                QuizContract.UsersTable.COLUMN_USERNAME + " = ?", new String[]{username});

        if (c.getCount() > 0) {
            long result = db.update(
                    QuizContract.UsersTable.TABLE_NAME,
                    cv,
                    QuizContract.UsersTable.COLUMN_USERNAME + " = ?",
                    new String[]{username}
            );
            //returns true if updated, otherwise false
            if (result != -1)
                return true;
            else
                return false;
        } else
            return false;
    }

    public boolean insertBookmark(String username, int categoryId, String difficulty, int questionId, String question, String answer) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.BookmarksTable.COLUMN_USERNAME, username);
        cv.put(QuizContract.BookmarksTable.COLUMN_CATEGORY_ID, categoryId);
        cv.put(QuizContract.BookmarksTable.COLUMN_DIFFICULTY, difficulty);
        cv.put(QuizContract.BookmarksTable.COLUMN_QUESTION_ID, questionId);
        cv.put(QuizContract.BookmarksTable.COLUMN_QUESTION, question);
        cv.put(QuizContract.BookmarksTable.COLUMN_ANSWER, answer);
        long result = db.insert(QuizContract.BookmarksTable.TABLE_NAME, null, cv);

        //returns true if inserted, otherwise returns false
        if (result != -1)
            return true;
        else
            return false;
    }

    public List<Bookmark> getBookmarks(String username) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.BookmarksTable.TABLE_NAME + " WHERE " +
                QuizContract.BookmarksTable.COLUMN_USERNAME + " = ? ", new String[]{username});

        if (c.moveToFirst()) {
            do {
                Bookmark bookmark = new Bookmark();
                bookmark.setUsername(c.getString(c.getColumnIndex(QuizContract.BookmarksTable.COLUMN_USERNAME)));
                bookmark.setCategoryId(c.getInt(c.getColumnIndex(QuizContract.BookmarksTable.COLUMN_CATEGORY_ID)));
                bookmark.setDifficulty(c.getString(c.getColumnIndex(QuizContract.BookmarksTable.COLUMN_DIFFICULTY)));
                bookmark.setQuestionId(c.getInt(c.getColumnIndex(QuizContract.BookmarksTable.COLUMN_QUESTION_ID)));
                bookmark.setQuestion(c.getString(c.getColumnIndex(QuizContract.BookmarksTable.COLUMN_QUESTION)));
                bookmark.setAnswer(c.getString(c.getColumnIndex(QuizContract.BookmarksTable.COLUMN_ANSWER)));
                bookmarkList.add(bookmark);
            } while (c.moveToNext());
        }
        c.close();
        return bookmarkList;
    }

    public boolean checkBookmark(int questionId) {
        db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.BookmarksTable.TABLE_NAME + " WHERE " +
                QuizContract.BookmarksTable.COLUMN_QUESTION_ID + " = ? ", new String[]{String.valueOf(questionId)});

        //return true if entry exists, otherwise returns false
        if (c.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean insertHistory(String username, int categoryId, String difficulty, int score) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.HistoryTable.COLUMN_USERNAME, username);
        cv.put(QuizContract.HistoryTable.COLUMN_CATEGORY_ID, categoryId);
        cv.put(QuizContract.HistoryTable.COLUMN_DIFFICULTY, difficulty);
        cv.put(QuizContract.HistoryTable.COLUMN_SCORE, score);

        long result = db.insert(QuizContract.HistoryTable.TABLE_NAME, null, cv);
        if (result != -1)
            return true;
        else
            return false;
    }

    public List<History> getHistory(String username) {
        List<History> historyList = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " +
                QuizContract.HistoryTable.TABLE_NAME + " WHERE " +
                QuizContract.HistoryTable.COLUMN_USERNAME + " = ? ", new String[]{username});

        if (c.moveToFirst()) {
            do {
                History history = new History();
                history.setUsername(c.getString(c.getColumnIndex(QuizContract.HistoryTable.COLUMN_USERNAME)));
                history.setCategoryId(c.getInt(c.getColumnIndex(QuizContract.HistoryTable.COLUMN_CATEGORY_ID)));
                history.setDifficulty(c.getString(c.getColumnIndex(QuizContract.HistoryTable.COLUMN_DIFFICULTY)));
                history.setScore(c.getInt(c.getColumnIndex(QuizContract.HistoryTable.COLUMN_SCORE)));
                historyList.add(history);
            } while (c.moveToNext());
        }
        c.close();
        return historyList;
    }

    public boolean insertScore(String username, int categoryId, String difficulty, int score) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.ScoreTable.COLUMN_USERNAME, username);
        cv.put(QuizContract.ScoreTable.COLUMN_CATEGORY_ID, categoryId);
        cv.put(QuizContract.ScoreTable.COLUMN_DIFFICULTY, difficulty);
        cv.put(QuizContract.ScoreTable.COLUMN_SCORE, score);
        long result = db.insert(QuizContract.ScoreTable.TABLE_NAME, null, cv);

        //returns true if inserted, otherwise returns false
        if (result != -1)
            return true;
        else
            return false;
    }

    public boolean checkScore(String username, int categoryId, String difficulty) {
        db = getWritableDatabase();

        String selection = QuizContract.ScoreTable.COLUMN_USERNAME + " = ? " +
                " AND " + QuizContract.ScoreTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.QuestionsTable.COLUMN_DIFFICULTY + " = ? ";

        String[] selectionArgs = new String[]{username, String.valueOf(categoryId), difficulty};
        Cursor c = db.query(
                QuizContract.ScoreTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        //returns true if entry exists, otherwise returns false
        if (c.getCount() > 0)
            return true;
        else
            return false;
    }

    public List<Score> getScores(int categoryId, String difficulty) {
        db = getReadableDatabase();
        List<Score> scoreList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " +
                        QuizContract.ScoreTable.TABLE_NAME + " INNER JOIN " +
                        QuizContract.UsersTable.TABLE_NAME + " ON " +
                        QuizContract.UsersTable.TABLE_NAME + "." + QuizContract.UsersTable.COLUMN_USERNAME + " = " +
                        QuizContract.ScoreTable.TABLE_NAME + "." + QuizContract.ScoreTable.COLUMN_USERNAME + " WHERE " +
                        QuizContract.ScoreTable.COLUMN_CATEGORY_ID + " = ? AND " +
                        QuizContract.ScoreTable.COLUMN_DIFFICULTY + " = ?" + " ORDER BY " + QuizContract.ScoreTable.COLUMN_SCORE +" DESC" + " LIMIT 3",
                new String[]{String.valueOf(categoryId), difficulty});

        if (c.moveToFirst()) {
            do {
                Score score = new Score();
                score.setName(c.getString(c.getColumnIndex(QuizContract.UsersTable.COLUMN_DISPLAY_NAME)));
                score.setCategoryId(c.getInt(c.getColumnIndex(QuizContract.ScoreTable.COLUMN_CATEGORY_ID)));
                score.setDifficulty(c.getString(c.getColumnIndex(QuizContract.ScoreTable.COLUMN_DIFFICULTY)));
                score.setScore(c.getInt(c.getColumnIndex(QuizContract.ScoreTable.COLUMN_SCORE)));
                score.setAvatar(c.getInt(c.getColumnIndex(QuizContract.UsersTable.COLUMN_AVATAR)));
                scoreList.add(score);
            } while (c.moveToNext());
        }
        c.close();
        return scoreList;
    }

    public List<Score> getTotalScores() {
        db = getReadableDatabase();
        List<Score> totalScoreList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT " +
                QuizContract.ScoreTable.TABLE_NAME + "." + QuizContract.ScoreTable.COLUMN_USERNAME + ", " +
                QuizContract.UsersTable.COLUMN_DISPLAY_NAME + ", " +
                QuizContract.UsersTable.COLUMN_AVATAR + ", SUM(" +
                QuizContract.ScoreTable.COLUMN_SCORE + ") as Total FROM " +
                QuizContract.ScoreTable.TABLE_NAME + " INNER JOIN " +
                QuizContract.UsersTable.TABLE_NAME + " ON " +
                QuizContract.UsersTable.TABLE_NAME + "." + QuizContract.UsersTable.COLUMN_USERNAME + " = " +
                QuizContract.ScoreTable.TABLE_NAME + "." + QuizContract.ScoreTable.COLUMN_USERNAME + " GROUP BY " +
                QuizContract.ScoreTable.TABLE_NAME + "." + QuizContract.ScoreTable.COLUMN_USERNAME + " ORDER BY Total DESC" +
                " LIMIT 3", null);

        if (c.moveToFirst()) {
            do {
                Score score = new Score();
                score.setName(c.getString(c.getColumnIndex(QuizContract.UsersTable.COLUMN_DISPLAY_NAME)));
                score.setScore(c.getInt(c.getColumnIndex("Total")));
                score.setAvatar(c.getInt(c.getColumnIndex(QuizContract.UsersTable.COLUMN_AVATAR)));
                totalScoreList.add(score);
            } while (c.moveToNext());
        }
        c.close();

        return totalScoreList;
    }

    public int getSavedScore(String username, int categoryId, String difficulty) {
        int x = 0;

        String selection = QuizContract.ScoreTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.ScoreTable.COLUMN_DIFFICULTY + " = ? ";

        String[] selectionArgs = new String[]{String.valueOf(categoryId), difficulty};
        String orderBy = QuizContract.ScoreTable.COLUMN_SCORE + " DESC";

        Cursor c = db.query(
                QuizContract.ScoreTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                orderBy,
                "3"
        );

        if (c.moveToFirst()) {
            x = c.getInt(c.getColumnIndex(QuizContract.ScoreTable.COLUMN_SCORE));
        }

        return x;
    }

    public boolean updateScore(String username, int categoryId, String difficulty, int score) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.ScoreTable.COLUMN_SCORE, score);

        String selection = QuizContract.ScoreTable.COLUMN_USERNAME + " = ? " +
                " AND " + QuizContract.ScoreTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.ScoreTable.COLUMN_DIFFICULTY + " = ? ";

        String[] selectionArgs = new String[]{username, String.valueOf(categoryId), difficulty};

        Cursor c = db.query(
                QuizContract.ScoreTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.getCount() > 0) {
            long result = db.update(
                    QuizContract.ScoreTable.TABLE_NAME,
                    cv,
                    QuizContract.ScoreTable.COLUMN_USERNAME + " = ?",
                    new String[]{username}
            );
            //returns true if updated, otherwise false
            if (result != -1)
                return true;
            else
                return false;
        } else
            return false;
    }
}
