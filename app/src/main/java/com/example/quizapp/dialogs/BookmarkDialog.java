package com.example.quizapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.quizapp.R;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.models.Bookmark;
import com.example.quizapp.models.Category;

public class BookmarkDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_bookmark, null);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(getActivity());
        TextView txtQuestion = view.findViewById(R.id.textQuestionBookmark);
        TextView txtAnswer = view.findViewById(R.id.textAnswerBookmark);

        Bookmark bookmark = getArguments().getParcelable(Constants.KEY_BOOKMARK);

        String question = bookmark.getQuestion();
        String answer = bookmark.getAnswer();
        int categoryId = bookmark.getCategoryId();
        Category category = dbHelper.getCategory(categoryId);
        String strCategory = category.getName();
        String difficulty = bookmark.getDifficulty();


        txtQuestion.setText(question);
        txtAnswer.setText("ANSWER: " + answer);

        builder.setTitle(strCategory + " - " + difficulty + " Level");

        builder.setView(view);
        return builder.create();
    }
}
