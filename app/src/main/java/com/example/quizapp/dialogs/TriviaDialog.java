package com.example.quizapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.quizapp.R;
import com.example.quizapp.activities.DashboardActivity;
import com.example.quizapp.models.Trivia;

import java.util.List;
import java.util.Random;

public class TriviaDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);


        List<Trivia> triviaList = DashboardActivity.triviaList;

        Random random = new Random();
        int min = 0;
        int max = triviaList.size();

        int randomNum = random.nextInt(max - min) + min;

        Trivia trivia1;
        trivia1 = triviaList.get(randomNum);
        String trivia = trivia1.getTrivia();
        builder.setTitle("Trivia").setMessage(trivia);

        return builder.create();
    }
}
