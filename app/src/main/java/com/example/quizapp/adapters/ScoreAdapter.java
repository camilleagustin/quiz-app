package com.example.quizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.DashboardActivity;
import com.example.quizapp.interfaces.OnItemClickListener;
import com.example.quizapp.models.Score;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreHolder> {
    private Context context;
    private List<Score> scores;
    private OnItemClickListener scoreListener;
    private int[] images = DashboardActivity.images;


    public ScoreAdapter(Context context, List<Score> scores) {
        this.context = context;
        this.scores = scores;
    }

    public void setOnHistoryItemClickListener(OnItemClickListener listener) {
        scoreListener = listener;
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_top_score, parent, false);
        return new ScoreHolder(view, scoreListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
        Score score = scores.get(position);
        String name = score.getName();
        int scorePts = score.getScore();
        int imageNumber = score.getAvatar();

        holder.txtName.setText(name);
        holder.txtScore.setText(String.valueOf(scorePts) + "\npoints");

        holder.imgAvatar.setImageResource(images[imageNumber]);

    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public static class ScoreHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtScore;
        public ImageView imgAvatar;

        public ScoreHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textName);
            txtScore = itemView.findViewById(R.id.textScore);
            imgAvatar = itemView.findViewById(R.id.imageViewAvatarScore);

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
