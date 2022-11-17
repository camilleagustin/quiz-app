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
import com.example.quizapp.models.Category;
import com.example.quizapp.models.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private Context context;
    private List<History> histories;
    private List<Category> categories;
    private OnItemClickListener historyListener;


    public HistoryAdapter(Context context, List<History> histories, List<Category> categories) {
        this.context = context;
        this.histories = histories;
        this.categories = categories;
    }

    public void setOnHistoryItemClickListener(OnItemClickListener listener) {
        historyListener = listener;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bookmark_history, parent, false);
        return new HistoryHolder(view, historyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        History history = histories.get(position);
        Category category = categories.get(history.getCategoryId() - 1);
        String strCategory = category.getName();
        int score = history.getScore();
        String difficulty = history.getDifficulty();

        String categoryDifficulty = strCategory + " - " + difficulty + " Level";
        String details = "Score: " + score;

        holder.txtCategoryDifficulty.setText(categoryDifficulty);
        holder.txtDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }


    public static class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView txtCategoryDifficulty;
        public TextView txtDetails;

        public HistoryHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtCategoryDifficulty = itemView.findViewById(R.id.textCategoryDifficulty);
            txtDetails = itemView.findViewById(R.id.textDetails);
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
