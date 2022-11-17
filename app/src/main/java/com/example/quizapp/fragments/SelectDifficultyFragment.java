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
import com.example.quizapp.models.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDifficultyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDifficultyFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int categoryId;

    public SelectDifficultyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectDifficultyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectDifficultyFragment newInstance(String param1, String param2) {
        SelectDifficultyFragment fragment = new SelectDifficultyFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_difficulty, container, false);

        TextView textCategory = view.findViewById(R.id.textCategory);

        Button btnEasy = view.findViewById(R.id.btnEasy);
        Button btnNormal = view.findViewById(R.id.btnNormal);
        Button btnExpert = view.findViewById(R.id.btnExpert);

        btnEasy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnExpert.setOnClickListener(this);

        //receives the data sent by HomeFragment from previous activity
        Bundle receivedIntent = getActivity().getIntent().getExtras();
        categoryId = receivedIntent.getInt(Constants.KEY_CATEGORY);

        QuizDbHelper quizDbHelper = QuizDbHelper.getInstance(getActivity());

        Category category = quizDbHelper.getCategory(categoryId);
        textCategory.setText(category.getName());

        return view;
    }

    @Override
    public void onClick(View v) {
        String selectedDifficulty = "";
        switch (v.getId()) {
            case R.id.btnEasy:
                selectedDifficulty = "Easy";
                break;
            case R.id.btnNormal:
                selectedDifficulty = "Normal";
                break;
            case R.id.btnExpert:
                selectedDifficulty = "Expert";
                break;
        }

        //packs the data into bundle to send the data to the preview fragment
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_DIFFICULTY, selectedDifficulty);
        bundle.putInt(Constants.KEY_CATEGORY, categoryId);
        Navigation.findNavController(v).navigate(R.id.action_selectDifficultyFragment_to_previewFragment, bundle);
    }
}