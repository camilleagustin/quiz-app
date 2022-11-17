package com.example.quizapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.DashboardActivity;
import com.example.quizapp.adapters.BookmarksAdapter;
import com.example.quizapp.constants.Constants;
import com.example.quizapp.dialogs.BookmarkDialog;
import com.example.quizapp.helpers.QuizDbHelper;
import com.example.quizapp.interfaces.OnItemClickListener;
import com.example.quizapp.models.Bookmark;
import com.example.quizapp.models.Category;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
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
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(getActivity());

        String username = DashboardActivity.sharedPreferences.getString(Constants.KEY_USERNAME, "DefaultValue");
        List<Bookmark> bookmarkList = dbHelper.getBookmarks(username);
        List<Category> categories = dbHelper.getAllCategories();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerBookmarks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BookmarksAdapter adapter = new BookmarksAdapter(getActivity(), bookmarkList, categories);

        if (bookmarkList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }

        adapter.setOnBookmarkItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bookmark bookmark = bookmarkList.get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_BOOKMARK, bookmark);

                BookmarkDialog bookmarkDialog = new BookmarkDialog();
                bookmarkDialog.setArguments(bundle);
                bookmarkDialog.show(getActivity().getSupportFragmentManager(), "bookmark dialog");
            }
        });

        return view;
    }
}