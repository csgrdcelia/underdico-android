package com.esgi.project.underdico.expression;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;
import com.esgi.project.underdico.newexpression.NewExpressionFragment;


public class ExpressionFragment extends Fragment implements ExpressionView {
    private static final String EXPRESSION = "expression";

    ExpressionPresenter presenter;

    private TextView tvDate;
    private TextView tvExpression;
    private TextView tvDefinition;
    private TextView tvTags;
    private TextView tvScore;
    private TextView tvUsername;
    private ImageButton ibOtherDefinition;
    private ImageButton ibDownvote;
    private ImageButton ibUpvote;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expression, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {
            assignViews();
            setListeners();
            Expression expression = (Expression) this.getArguments().getSerializable(EXPRESSION);
            presenter = new ExpressionPresenter(this, expression);
        } else {
            Toast.makeText(getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            goHome();
        }
    }

    private void assignViews() {
        tvDate = getView().findViewById(R.id.tvExpressionDate);
        tvExpression = getView().findViewById(R.id.tvExpression);
        tvDefinition = getView().findViewById(R.id.tvExpressionDefinition);
        tvTags = getView().findViewById(R.id.tvTags);
        tvScore = getView().findViewById(R.id.tvExpressionScore);
        tvUsername = getView().findViewById(R.id.tvUsername);
        ibOtherDefinition = getView().findViewById(R.id.ibAddDef);
        ibDownvote = getView().findViewById(R.id.ibDownVote);
        ibUpvote = getView().findViewById(R.id.ibUpVote);
    }

    private void setListeners() {
        ibOtherDefinition.setOnClickListener(v -> presenter.createOtherDefinition());
        ibDownvote.setOnClickListener(v -> presenter.tryVote(Vote.Type.DOWN));
        ibUpvote.setOnClickListener(v -> presenter.tryVote(Vote.Type.UP));
    }

    public void displayExpression(Expression expression) {
        tvDate.setText(expression.getCreatedAt());
        tvExpression.setText(expression.getLabel());
        tvDefinition.setText(expression.getDefinition());
        tvTags.setText(expression.getTags());
        tvScore.setText(String.valueOf(expression.getScore()));
        tvUsername.setText(expression.getUser().getUsername());
    }

    public void displayAlreadyVoted(Vote.Type type) {
        ibDownvote.setEnabled(type == Vote.Type.DOWN ? false : true);
        ibUpvote.setEnabled(type == Vote.Type.UP ? false : true);
    }

    public void goToNewExpressionView(Expression expression) {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = NewExpressionFragment.newInstance(expression);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    public void goHome() {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = HomeFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void setScore(int vote) {
        tvScore.setText(String.valueOf(vote));
    }

    @Override
    public void showAPIError() {
        Toast.makeText(getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
    }


}
