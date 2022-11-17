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
import com.example.quizapp.models.Question;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersHolder> {
    private Context context;
    private List<Question> questions;
    private OnItemClickListener answerListener;


    public AnswersAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    public void setOnAnswerItemClickListener(OnItemClickListener listener) {
        answerListener = listener;
    }

    @NonNull
    @Override
    public AnswersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_answer, parent, false);
        return new AnswersHolder(view, answerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersHolder holder, int position) {
        Question question = questions.get(position);
        int x = position + 1;
        holder.txtQuestion.setText("Q " + x + " - " + question.getQuestion());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


    public static class AnswersHolder extends RecyclerView.ViewHolder {
        public TextView txtQuestion;

        public AnswersHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.textQuestion);
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
