package com.example.quizapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quizapp.R;
import com.example.quizapp.activities.QuizActivity;
import com.example.quizapp.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton btn1 = view.findViewById(R.id.btnPython);
        ImageButton btn2 = view.findViewById(R.id.btnJava);
        ImageButton btn3 = view.findViewById(R.id.btnC);
        ImageButton btn4 = view.findViewById(R.id.btnCPlusPlus);
        ImageButton btn6 = view.findViewById(R.id.btnSwift);
        ImageButton btn7 = view.findViewById(R.id.btnGo);
        ImageButton btn9 = view.findViewById(R.id.btnSQL);
        ImageButton btn10 = view.findViewById(R.id.btnObjectC);
        ImageButton btn11 = view.findViewById(R.id.btnJavaScript);
        ImageButton btn12 = view.findViewById(R.id.btnObjectPascal);
        ImageButton btn13 = view.findViewById(R.id.btnVbNet);
        ImageButton btn14 = view.findViewById(R.id.btnHTML);
        ImageButton btn15 = view.findViewById(R.id.btnCSharp);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        //sends the category id of the selected button
        int selectedCategoryId = 0;
        switch (v.getId()) {
            case R.id.btnPython:
                selectedCategoryId = 1;
                break;
            case R.id.btnJava:
                selectedCategoryId = 2;
                break;
            case R.id.btnC:
                selectedCategoryId = 3;
                break;
            case R.id.btnCPlusPlus:
                selectedCategoryId = 4;
                break;
            case R.id.btnCSharp:
                selectedCategoryId = 5;
                break;
            case R.id.btnSwift:
                selectedCategoryId = 7;
                break;
            case R.id.btnGo:
                selectedCategoryId = 8;
                break;
            case R.id.btnSQL:
                selectedCategoryId = 10;
                break;
            case R.id.btnObjectC:
                selectedCategoryId = 11;
                break;
            case R.id.btnJavaScript:
                selectedCategoryId = 12;
                break;
            case R.id.btnObjectPascal:
                selectedCategoryId = 13;
                break;
            case R.id.btnVbNet:
                selectedCategoryId = 14;
                break;
            case R.id.btnHTML:
                selectedCategoryId = 15;
                break;
        }
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra(Constants.KEY_CATEGORY, selectedCategoryId);
        startActivity(intent);
    }
}

