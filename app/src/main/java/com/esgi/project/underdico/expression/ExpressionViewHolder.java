package com.esgi.project.underdico.expression;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;

public class ExpressionViewHolder extends RecyclerView.ViewHolder {

    private Expression expression;
    private ExpressionClickListener listener;
    private TextView tvDate;
    private TextView tvExpression;
    private TextView tvDefinition;
    private TextView tvTags;
    private TextView tvScore;
    private TextView tvUsername;


    public ExpressionViewHolder(@NonNull View itemView, ExpressionClickListener listener, Context context) {
        super(itemView);
        this.listener = listener;
        assignViews();
        setOnClickListeners();
    }

    private void assignViews() {
        tvDate = itemView.findViewById(R.id.tvExpressionDate);
        tvExpression = itemView.findViewById(R.id.tvExpression);
        tvDefinition = itemView.findViewById(R.id.tvExpressionDefinition);
        tvTags = itemView.findViewById(R.id.tvTags);
        tvScore = itemView.findViewById(R.id.tvExpressionScore);
        tvUsername = itemView.findViewById(R.id.tvUsername);
    }

    public void bind(Expression expression) {
        this.expression = expression;
        tvDate.setText(expression.getCreatedAt());
        tvExpression.setText(expression.getLabel());
        tvDefinition.setText(expression.getDefinition());
        tvTags.setText(expression.getTags());//TODO: tag system
        tvScore.setText(String.valueOf(expression.getScore()));
        tvUsername.setText(expression.getUser().getUsername());
    }

    private void setOnClickListeners() {
        tvExpression.setOnClickListener(v -> listener.onClick(v, expression));
    }


}
