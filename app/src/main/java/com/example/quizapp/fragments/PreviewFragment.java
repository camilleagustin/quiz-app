package com.example.quizapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.R;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.models.Question;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView textQuestion;
    private TextView textQuestion2;
    private Button btnStart;
    private List<Question> questionList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviewFragment newInstance(String param1, String param2) {
        PreviewFragment fragment = new PreviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        TextView textLevel = view.findViewById(R.id.textDifficulty);
        TextView textQuestionTotal = view.findViewById(R.id.textTotalQuestions);
        TextView textCategory = view.findViewById(R.id.textViewCategory);
        textQuestion = view.findViewById(R.id.textQuestionPreview);
        textQuestion2 = view.findViewById(R.id.textQuestionPreview2);


        //receives data sent by the selectdifficulty fragment
        String difficulty = getArguments().getString(Constants.KEY_DIFFICULTY);
        int categoryId = getArguments().getInt(Constants.KEY_CATEGORY);

        //gets or creates an instance of the database
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(getActivity());
        questionList = dbHelper.getQuestions(categoryId, difficulty);

        textLevel.setText(difficulty + " Level");
        textQuestionTotal.setText(Integer.toString(questionList.size()) + " Questions");
        ;
        switch (categoryId) {
            case 1:
                textCategory.setText("Python");
                break;
            case 2:
                textCategory.setText("Java");
                break;
            case 3:
                textCategory.setText("C");
                break;
            case 4:
                textCategory.setText("C++");
                break;
            case 5:
                textCategory.setText("C#");
                break;
            case 6:
                textCategory.setText("Ruby");
                break;
            case 7:
                textCategory.setText("Swift");
                break;
            case 8:
                textCategory.setText("Go");
                break;
            case 9:
                textCategory.setText("R");
                break;
            case 10:
                textCategory.setText("SQL");
                break;
            case 11:
                textCategory.setText("Objective-C");
                break;
            case 12:
                textCategory.setText("JavaScript");
                break;
            case 13:
                textCategory.setText("Object Pascal");
                break;
            case 14:
                textCategory.setText("VB.NET");
                break;
            case 15:
                textCategory.setText("HTML");
                break;
            case 16:
                textCategory.setText("PHP");
                break;
        }

        showQuestions();

        btnStart = view.findViewById(R.id.btnStart);

        //sends data to quiz fragment when clicking start button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_DIFFICULTY, difficulty);
                bundle.putInt(Constants.KEY_CATEGORY, categoryId);
                Navigation.findNavController(v).navigate(R.id.action_previewFragment_to_quizFragment, bundle);
            }
        });

        return view;
    }

    //selects 2 questions to be displayed as a preview
    private void showQuestions() {
        Question currentQuestion = questionList.get(0);
        Question currentQuestion2 = questionList.get(1);
        textQuestion.setText(currentQuestion.getQuestion());
        textQuestion2.setText(currentQuestion2.getQuestion());
    }
}