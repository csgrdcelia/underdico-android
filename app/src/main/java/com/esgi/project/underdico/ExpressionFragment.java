package com.esgi.project.underdico;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esgi.project.underdico.models.Expression;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpressionFragment extends Fragment {
    public static final String EXPRESSION = "expression";

    private Expression expression;
    private TextView tvDate;
    private TextView tvExpression;
    private TextView tvDefinition;
    private TextView tvTags;
    private TextView tvScore;
    private TextView tvUsername;

    public ExpressionFragment() {
        // Required empty public constructor
    }

    public static ExpressionFragment newInstance(Expression expression) {
        Bundle args = new Bundle();
        args.putSerializable(EXPRESSION, expression);
        ExpressionFragment fragment = new ExpressionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        expression = (Expression) this.getArguments().getSerializable(EXPRESSION);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expression, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assignViews();
        displayExpression();
    }

    private void assignViews() {
        tvDate = getView().findViewById(R.id.tvExpressionDate);
        tvExpression = getView().findViewById(R.id.tvExpression);
        tvDefinition = getView().findViewById(R.id.tvExpressionDefinition);
        tvTags = getView().findViewById(R.id.tvTags);
        tvScore = getView().findViewById(R.id.tvExpressionScore);
        tvUsername = getView().findViewById(R.id.tvUsername);
    }

    private void displayExpression() {
        tvDate.setText(expression.getCreatedAt());
        tvExpression.setText(expression.getLabel());
        tvDefinition.setText(expression.getDefinition());
        tvTags.setText(expression.getTags());
        tvScore.setText(String.valueOf(expression.getScore()));
        tvUsername.setText(expression.getUser().getUsername());

    }
}
