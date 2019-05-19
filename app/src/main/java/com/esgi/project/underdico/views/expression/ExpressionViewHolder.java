package com.esgi.project.underdico.views.expression;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.views.search.SearchFragment;
import com.esgi.project.underdico.presenters.ExpressionPresenter;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class ExpressionViewHolder extends RecyclerView.ViewHolder implements ExpressionView {

    private Context context;
    private ExpressionPresenter presenter;
    private Expression expression;
    private ExpressionClickListener listener;
    private TextView tvDate;
    private TextView tvExpression;
    private TextView tvDefinition;
    private FlexboxLayout tagLayout;
    private TextView tvScore;
    private TextView tvUsername;
    private ImageButton ibDownvote;
    private ImageButton ibUpvote;

    private View.OnClickListener tagListener;


    public ExpressionViewHolder(@NonNull View itemView, ExpressionClickListener listener, Context context) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        assignViews();
        setOnClickListeners();
    }

    private void assignViews() {
        tvDate = itemView.findViewById(R.id.tvExpressionDate);
        tvExpression = itemView.findViewById(R.id.tvExpression);
        tvDefinition = itemView.findViewById(R.id.tvExpressionDefinition);
        tagLayout = itemView.findViewById(R.id.flexBoxTags);
        tvScore = itemView.findViewById(R.id.tvExpressionScore);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        ibDownvote = itemView.findViewById(R.id.ibDownvote);
        ibUpvote = itemView.findViewById(R.id.ibUpvote);
    }

    public void bind(Expression expression) {
        this.expression = expression;
        presenter = new ExpressionPresenter(this, expression, context);

    }

    private void setOnClickListeners() {
        tagListener = v -> presenter.searchExpressionsWithTag(String.valueOf(((TextView)v).getText()));
        tvExpression.setOnClickListener(v -> listener.onClick(v, expression));
        ibDownvote.setOnClickListener(v -> presenter.tryVote(false));
        ibUpvote.setOnClickListener(v -> presenter.tryVote(true));
    }


    @Override
    public void displayExpression(Expression expression) {
        tvDate.setText(expression.getCreatedAt());
        tvExpression.setText(expression.getLabel());
        tvDefinition.setText(expression.getDefinition());
        tvScore.setText(String.valueOf(expression.getScore()));
        //tvUsername.setText(expression.getUser().getUsername());
        tvUsername.setText(expression.getUserId()); //TODO: get user when in API
    }

    @Override
    public void displayTags(String[] tags) {
        if(tags != null) {
            for (String tag : tags) {
                TextView textView = createTextViewTag(tag);
                tagLayout.addView(textView);
            }
        }
    }

    private TextView createTextViewTag(String tag) {
        TextView textView = new TextView(context);
        int id = View.generateViewId();
        textView.setId(id);
        textView.setText(tag);
        textView.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_primary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textView.setLayoutParams(params);
        textView.setTextSize(14);
        textView.setOnClickListener(tagListener);
        return textView;
    }

    @Override
    public void displayAlreadyVoted(boolean score) {
        ibDownvote.setEnabled(score == false ? false : true);
        ibUpvote.setEnabled(score == true ? false : true);
    }

    @Override
    public void setScore(int vote) {
        tvScore.setText(String.valueOf(vote));
    }

    @Override
    public void searchWithTag(String tag, List<Expression> expressions) {
        MainActivity activity = (MainActivity) context;
        Fragment myFragment = SearchFragment.newInstance(tag, expressions);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}