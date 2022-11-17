package com.example.quizapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.quizapp.R;
import com.example.quizapp.activities.QuizActivity;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.models.Question;

public class AnswersDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_answers, null);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(getActivity());

        TextView textQuestion = view.findViewById(R.id.textAnswerQuestion);
        TextView textOptions = view.findViewById(R.id.textAnswerOptions);
        TextView textAnswer = view.findViewById(R.id.textCorrectAnswer);
        ImageButton btnBookmark = view.findViewById(R.id.btnBookmark);

        Question question = getArguments().getParcelable(Constants.KEY_QUESTION);

        int questionId = question.getId();
        int categoryId = question.getCategoryId();
        String difficulty = question.getDifficulty();
        String strQuestion = question.getQuestion();
        String option1 = question.getOption1();
        String option2 = question.getOption2();
        String option3 = question.getOption3();
        String option4 = question.getOption4();
        String answer = question.getCorrectAnswer();

        boolean checkBookmark = dbHelper.checkBookmark(questionId);

        if (checkBookmark == true) {
            btnBookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        }

        textQuestion.setText(strQuestion);
        String options = option1 + "\n" + option2 + "\n";
        if (option3 != null) {
            options += option3 + "\n";
        }
        if (option4 != null) {
            options += option4 + "\n";
        }
        textOptions.setText(options);
        textAnswer.setText("ANSWER: " + answer);

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = QuizActivity.sharedPreferences.getString(Constants.KEY_USERNAME, "DefaultValue");

                boolean insertBookmark = dbHelper.insertBookmark(username, categoryId, difficulty, questionId, strQuestion, answer);
                if (insertBookmark == true) {
                    Toast.makeText(getActivity(), "Bookmarked!", Toast.LENGTH_SHORT).show();
                    btnBookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
                } else {
                    Toast.makeText(getActivity(), "Bookmark already exists", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(view);
        return builder.create();
    }
}
