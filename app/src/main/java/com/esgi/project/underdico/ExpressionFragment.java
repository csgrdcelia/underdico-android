package com.esgi.project.underdico;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.models.Expression;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpressionFragment extends Fragment {
    public static final String EXPRESSION = "expression";

    private Expression expression;

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

}
