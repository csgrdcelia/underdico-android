package com.esgi.project.underdico.views.expression;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.views.search.SearchFragment;
import com.esgi.project.underdico.presenters.ExpressionPresenter;
import com.esgi.project.underdico.views.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.newexpression.NewExpressionFragment;
import com.esgi.project.underdico.views.user.UserFragment;
import com.google.android.flexbox.FlexboxLayout;


public class ExpressionFragment extends Fragment implements DetailedExpressionView {
    private static final String EXPRESSION = "expression";

    ExpressionPresenter presenter;

    private TextView tvDate;
    private TextView tvExpression;
    private TextView tvDefinition;
    private TextView tvScore;
    private TextView tvUsername;
    private ImageButton ibOtherDefinition;
    private ImageButton ibDownvote;
    private ImageButton ibUpvote;
    private ImageButton ibAudio;
    private ImageView ivLocaleFlag;
    private FlexboxLayout tagLayout;

    private View.OnClickListener tagListener;

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
            Expression expression = (Expression) this.getArguments().getSerializable(EXPRESSION);
            presenter = new ExpressionPresenter(this, expression, getContext(), null);
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();
            goHome();
        }
    }

    @Override
    public void assignViews() {
        tvDate = getView().findViewById(R.id.tvExpressionDate);
        tvExpression = getView().findViewById(R.id.tvExpression);
        tvDefinition = getView().findViewById(R.id.tvExpressionDefinition);
        tvScore = getView().findViewById(R.id.tvExpressionScore);
        tvUsername = getView().findViewById(R.id.tvUsername);
        ibOtherDefinition = getView().findViewById(R.id.ibAddDef);
        ibDownvote = getView().findViewById(R.id.ibDownvote);
        ibUpvote = getView().findViewById(R.id.ibUpvote);
        tagLayout = getView().findViewById(R.id.flexBoxTags);
        ibAudio = getView().findViewById(R.id.ibAudio);
        ivLocaleFlag = getView().findViewById(R.id.ivLocaleFlag);
    }

    @Override
    public void setListeners() {
        tagListener = v -> presenter.searchExpressionsWithTag(String.valueOf(((TextView)v).getText()));
        ibOtherDefinition.setOnClickListener(v -> presenter.createOtherDefinition());
        ibDownvote.setOnClickListener(v -> presenter.tryVote(false));
        ibUpvote.setOnClickListener(v -> presenter.tryVote(true));
        tvUsername.setOnClickListener(v -> redirectToUserPage(presenter.getExpression().getUser()));
        ibAudio.setOnClickListener(v -> presenter.playAudio());
    }

    @Override
    public void displayExpression(Expression expression) {
        tvDate.setText(expression.getCreatedAt());
        tvExpression.setText(expression.getWord());
        tvDefinition.setText(expression.getDefinition());
        tvScore.setText(String.valueOf(expression.getScore()));
        ivLocaleFlag.setImageDrawable(getContext().getDrawable(expression.getFlagImage()));
    }

    @Override
    public void displayTags(String[] tags) {
        if(tags != null) {
            tagLayout.removeAllViews();
            for (String tag : tags) {
                TextView textView = createTextViewTag(tag);
                tagLayout.addView(textView);
            }
        }
    }

    private TextView createTextViewTag(String tag) {
        TextView textView = new TextView(getContext());
        int id = View.generateViewId();
        textView.setId(id);
        textView.setText(tag);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.design_default_color_primary));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textView.setLayoutParams(params);
        textView.setTextSize(18);
        textView.setOnClickListener(tagListener);
        return textView;
    }

    public void displayUserVote(boolean voteType) {
        ibUpvote.setImageResource( voteType == true ? R.drawable.ic_arrow_up_green : R.drawable.ic_arrow_up);
        ibDownvote.setImageResource( voteType == false ? R.drawable.ic_arrow_down_red : R.drawable.ic_arrow_down);
        ibDownvote.setEnabled(voteType == false ? false : true);
        ibUpvote.setEnabled(voteType == true ? false : true);
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
    public void searchWithTag(String tag) {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = SearchFragment.newInstance(tag);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void redirectToUserPage(User user) {
        MainActivity activity = (MainActivity) getContext();
        Fragment myFragment = UserFragment.newInstance(user);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}
