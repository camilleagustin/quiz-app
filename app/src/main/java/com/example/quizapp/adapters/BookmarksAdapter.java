package com.example.quizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.interfaces.OnItemClickListener;
import com.example.quizapp.models.Bookmark;
import com.example.quizapp.models.Category;
import com.example.quizapp.models.Question;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksHolder> {
    private Context context;
    private List<Bookmark> bookmarks;
    private List<Category> categories;
    private OnItemClickListener bookmarkListener;


    public BookmarksAdapter(Context context, List<Bookmark> bookmarks, List<Category> categories) {
        this.context = context;
        this.bookmarks = bookmarks;
        this.categories = categories;

    }

    public void setOnBookmarkItemClickListener(OnItemClickListener listener) {
        bookmarkListener = listener;
    }

    @NonNull
    @Override
    public BookmarksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bookmark_history, parent, false);
        return new BookmarksAdapter.BookmarksHolder(view, bookmarkListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);
        Category category = categories.get(bookmark.getCategoryId() - 1);

        String categoryName = category.getName();
        String difficulty = bookmark.getDifficulty();

        String categoryDifficulty = categoryName + " - " + difficulty + " Level";
        String strQuestion =bookmark.getQuestion();

        holder.txtCategoryDifficulty.setText(categoryDifficulty);
        holder.txtQuestion.setText(strQuestion);
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


    public static class BookmarksHolder extends RecyclerView.ViewHolder {
        public TextView txtCategoryDifficulty;
        public TextView txtQuestion;

        public BookmarksHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtCategoryDifficulty = itemView.findViewById(R.id.textCategoryDifficulty);
            txtQuestion = itemView.findViewById(R.id.textDetails);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
