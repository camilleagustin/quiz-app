package com.example.quizapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.adapters.AnswersAdapter;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.dialogs.AnswersDialog;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.interfaces.OnItemClickListener;
import com.example.quizapp.models.Question;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnswersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnswersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnswersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswersFragment newInstance(String param1, String param2) {
        AnswersFragment fragment = new AnswersFragment();
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
        View view = inflater.inflate(R.layout.fragment_answers, container, false);

        int categoryId = getArguments().getInt(Constants.KEY_CATEGORY);
        String difficulty = getArguments().getString(Constants.KEY_DIFFICULTY);

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(getActivity());
        List<Question> questionList = dbHelper.getQuestions(categoryId, difficulty);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerAnswers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AnswersAdapter adapter = new AnswersAdapter(getActivity(), questionList);
        recyclerView.setAdapter(adapter);

        adapter.setOnAnswerItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Question question = questionList.get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_QUESTION, question);

                AnswersDialog answersDialog = new AnswersDialog();
                answersDialog.setArguments(bundle);
                answersDialog.show(getActivity().getSupportFragmentManager(), "answer dialog");
            }

        });

        return view;
    }
}