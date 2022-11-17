package com.example.quizapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.R;
import com.example.quizapp.activities.QuizActivity;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.models.Question;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final long COUNTDOWN_TIME_MILLIS = 11000;

    private TextView txtQuestion;
    private TextView txtQuestionCount;
    private TextView txtCountDownTimer;
    private Button btnOption1;
    private Button btnOption2;
    private Button btnOption3;
    private Button btnOption4;
    private CountDownTimer timer;

    QuizDbHelper dbHelper;

    private int categoryId;
    private String difficulty;
    private long timeMillis;
    private long timeDelay;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score = 0;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        txtQuestion = view.findViewById(R.id.textQuestion);
        txtQuestionCount = view.findViewById(R.id.textQuestionCount);
        txtCountDownTimer = view.findViewById(R.id.textTimer);

        btnOption1 = view.findViewById(R.id.btn1);
        btnOption2 = view.findViewById(R.id.btn2);
        btnOption3 = view.findViewById(R.id.btn3);
        btnOption4 = view.findViewById(R.id.btn4);
        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);

        difficulty = getArguments().getString(Constants.KEY_DIFFICULTY);
        categoryId = getArguments().getInt(Constants.KEY_CATEGORY);

        dbHelper = QuizDbHelper.getInstance(getActivity());
        questionList = dbHelper.getQuestions(categoryId, difficulty);
        Collections.shuffle(questionList);

        questionCountTotal = questionList.size();
        showNextQuestion();

        return view;
    }

    //shows the next question to be displayed until it reaches maximum number of questions
    private void showNextQuestion() {
        if (questionCounter < questionCountTotal) {
            resetButtons();

            currentQuestion = questionList.get(questionCounter);
            txtQuestion.setText(currentQuestion.getQuestion());
            btnOption1.setText(currentQuestion.getOption1());
            btnOption2.setText(currentQuestion.getOption2());

            if (currentQuestion.getOption3() != null) {
                btnOption3.setVisibility(View.VISIBLE);
                btnOption3.setText(currentQuestion.getOption3());
            } else {
                btnOption3.setVisibility(View.INVISIBLE);
            }

            if (currentQuestion.getOption4() != null) {
                btnOption4.setVisibility(View.VISIBLE);
                btnOption4.setText(currentQuestion.getOption4());
            } else {
                btnOption4.setVisibility(View.INVISIBLE);
            }

            timeMillis = COUNTDOWN_TIME_MILLIS;
            startTimer();

            questionCounter++;

            String questionCount = "Question:\n" + questionCounter + "/" + questionCountTotal;
            txtQuestionCount.setText(questionCount);
        } else {
            finishQuiz();
        }
    }

    private void resetButtons() {
        btnOption1.setBackgroundColor(getResources().getColor(R.color.purple_200));
        btnOption2.setBackgroundColor(getResources().getColor(R.color.purple_200));
        btnOption3.setBackgroundColor(getResources().getColor(R.color.purple_200));
        btnOption4.setBackgroundColor(getResources().getColor(R.color.purple_200));

        btnOption1.setClickable(true);
        btnOption2.setClickable(true);
        btnOption3.setClickable(true);
        btnOption4.setClickable(true);

    }

    //navigates to score fragment when quiz is finished
    private void finishQuiz() {
        String username = QuizActivity.sharedPreferences.getString(Constants.KEY_USERNAME, "DefaultValue");
        dbHelper.insertHistory(username, categoryId, difficulty, score);

        boolean scoreExists = dbHelper.checkScore(username, categoryId, difficulty);
        if (!scoreExists)
            dbHelper.insertScore(username, categoryId, difficulty, score);
        else {
            Toast.makeText(getActivity(), "asdfasdf", Toast.LENGTH_SHORT).show();
            int savedScore = dbHelper.getSavedScore(username, categoryId, difficulty);

            if (savedScore < score) {
                dbHelper.updateScore(username, categoryId, difficulty, score);
                Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
            }
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_SCORE, score);
        bundle.putInt(Constants.KEY_TOTAL, questionCountTotal);
        bundle.putInt(Constants.KEY_CATEGORY, categoryId);
        bundle.putString(Constants.KEY_DIFFICULTY, difficulty);
        Navigation.findNavController(getView()).navigate(R.id.action_quizFragment_to_scoreFragment, bundle);
    }

    //gets the selected button and checks for the answer
    @Override
    public void onClick(View v) {
        String selectedAnswer;
        switch (v.getId()) {
            case R.id.btn1:
                selectedAnswer = btnOption1.getText().toString();
                checkAnswer(selectedAnswer, v);
                break;
            case R.id.btn2:
                selectedAnswer = btnOption2.getText().toString();
                checkAnswer(selectedAnswer, v);
                break;
            case R.id.btn3:
                selectedAnswer = btnOption3.getText().toString();
                checkAnswer(selectedAnswer, v);
                break;
            case R.id.btn4:
                selectedAnswer = btnOption4.getText().toString();
                checkAnswer(selectedAnswer, v);
        }
    }

    private void checkAnswer(String answer, View v) {
        timer.cancel();
        timeMillis = 0;
        updateTimer();

        String correctAnswer = currentQuestion.getCorrectAnswer();
        if (answer.equals(correctAnswer)) {
            score++;
            switch (v.getId()) {
                case R.id.btn1:
                    btnOption1.setBackgroundColor(Color.GREEN);
                    break;
                case R.id.btn2:
                    btnOption2.setBackgroundColor(Color.GREEN);
                    break;
                case R.id.btn3:
                    btnOption3.setBackgroundColor(Color.GREEN);
                    break;
                case R.id.btn4:
                    btnOption4.setBackgroundColor(Color.GREEN);
            }
        } else {
            switch (v.getId()) {
                case R.id.btn1:
                    btnOption1.setBackgroundColor(Color.RED);
                    break;
                case R.id.btn2:
                    btnOption2.setBackgroundColor(Color.RED);
                    break;
                case R.id.btn3:
                    btnOption3.setBackgroundColor(Color.RED);
                    break;
                case R.id.btn4:
                    btnOption4.setBackgroundColor(Color.RED);
                    break;
            }
            showCorrectAnswer();
        }
        delayTimer();
    }

    private void showCorrectAnswer() {
        String correctAnswer = currentQuestion.getCorrectAnswer();
        if ((btnOption1.getText().toString()).equals(correctAnswer)) {
            btnOption1.setBackgroundColor(Color.GREEN);
        } else if ((btnOption2.getText().toString()).equals(correctAnswer)) {
            btnOption2.setBackgroundColor(Color.GREEN);
        } else if ((btnOption3.getText().toString()).equals(correctAnswer)) {
            btnOption3.setBackgroundColor(Color.GREEN);
        } else if ((btnOption4.getText().toString()).equals(correctAnswer)) {
            btnOption4.setBackgroundColor(Color.GREEN);
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                showCorrectAnswer();
                delayTimer();
            }
        }.start();
    }

    private void delayTimer() {
        btnOption1.setClickable(false);
        btnOption2.setClickable(false);
        btnOption3.setClickable(false);
        btnOption4.setClickable(false);

        timeDelay = 1000;
        new CountDownTimer(timeDelay, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeDelay = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeMillis / 1000) / 60;
        int seconds = (int) (timeMillis / 1000) % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        txtCountDownTimer.setText(timeLeft);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}