package com.esgi.project.underdico.views.expression;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.home.HomeView;

import java.util.List;

public class ExpressionAdapter extends RecyclerView.Adapter<ExpressionViewHolder> {
    private List<Expression> expressions;
    private Context context;
    private ViewClickListener listener;
    private HomeView parent;

    public ExpressionAdapter(List<Expression> expressions, ViewClickListener listener, Context context, HomeView parent)
    {
        this.expressions = expressions;
        this.context = context;
        this.listener = listener;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ExpressionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_expression_preview, viewGroup, false);
        return new ExpressionViewHolder(view, listener, context, parent);
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
