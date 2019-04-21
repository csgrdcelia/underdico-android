package com.esgi.project.underdico;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

public class ExpressionAdapter extends RecyclerView.Adapter<ExpressionViewHolder> {
    private List<Expression> expressions;
    private Context context;

    public ExpressionAdapter(List<Expression> expressions, Context context)
    {
        this.expressions = expressions;
        this.context = context;
    }


    @NonNull
    @Override
    public ExpressionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_expression, viewGroup, false);
        return new ExpressionViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpressionViewHolder expressionViewHolder, int i) {
        Expression expression = expressions.get(i);
        expressionViewHolder.bind(expression);
    }

    @Override
    public int getItemCount() {
        return expressions.size();
    }
}
