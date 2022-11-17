package com.example.quizapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.quizapp.R;
import com.example.quizapp.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        ConstraintLayout constraintLayout = view.findViewById(R.id.layout);
        TextView textScore = view.findViewById(R.id.textScore);
        Button btnViewRankings = view.findViewById(R.id.btnViewRankings);
        RatingBar rating = view.findViewById(R.id.ratingBar);

        int score = getArguments().getInt(Constants.KEY_SCORE);
        int total = getArguments().getInt(Constants.KEY_TOTAL);
        int categoryId = getArguments().getInt(Constants.KEY_CATEGORY);
        String difficulty = getArguments().getString(Constants.KEY_DIFFICULTY);

        float scoreRating = ((float) score / total) * ((float) 5);
        rating.setRating(scoreRating);

        String scoreTotal = score + "/" + total;
        textScore.setText("SCORE: " + scoreTotal);


        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.KEY_CATEGORY, categoryId);
                bundle.putString(Constants.KEY_DIFFICULTY, difficulty);
                Navigation.findNavController(v).navigate(R.id.action_scoreFragment_to_answersFragment, bundle);
                return false;
            }

        });

        btnViewRankings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_DIFFICULTY, difficulty);
                bundle.putInt(Constants.KEY_CATEGORY, categoryId);
                Navigation.findNavController(v).navigate(R.id.action_scoreFragment_to_rankingFragment, bundle);
            }
        });
        return view;
    }

}