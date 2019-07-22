package com.esgi.project.underdico.views.expression;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.views.home.HomeView;
import com.esgi.project.underdico.views.search.SearchFragment;
import com.esgi.project.underdico.presenters.ExpressionPresenter;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.user.UserFragment;
import com.google.android.flexbox.FlexboxLayout;

import java.util.logging.LogManager;

public class ExpressionViewHolder extends RecyclerView.ViewHolder implements ExpressionView {

    private Context context;
    private HomeView parent;
    private ExpressionPresenter presenter;
    private Expression expression;
    private ViewClickListener listener;
    private TextView tvDate;
    private TextView tvExpression;
    private TextView tvDefinition;
    private FlexboxLayout tagLayout;
    private TextView tvScore;
    private TextView tvUsername;
    private ImageButton ibDownvote;
    private ImageButton ibUpvote;
    private ImageButton ibAudio;
    private ImageView ivLocaleFlag;

    private View.OnClickListener tagListener;


    public ExpressionViewHolder(@NonNull View itemView, ViewClickListener listener, Context context, HomeView parent) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        this.parent = parent;
    }

    public void bind(Expression expression) {
        this.expression = expression;
        presenter = new ExpressionPresenter(this, expression, context, parent);
    }

    @Override
    public void assignViews() {
        tvDate = itemView.findViewById(R.id.tvExpressionDate);
        tvExpression = itemView.findViewById(R.id.tvExpression);
        tvDefinition = itemView.findViewById(R.id.tvExpressionDefinition);
        tagLayout = itemView.findViewById(R.id.flexBoxTags);
        tvScore = itemView.findViewById(R.id.tvExpressionScore);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        ibDownvote = itemView.findViewById(R.id.ibDownvote);
        ibUpvote = itemView.findViewById(R.id.ibUpvote);
        ibAudio = itemView.findViewById(R.id.ibAudio);
        ivLocaleFlag = itemView.findViewById(R.id.ivLocaleFlag);
    }

    @Override
    public void setListeners() {
        tagListener = v -> presenter.searchExpressionsWithTag(String.valueOf(((TextView)v).getText()));
        tvExpression.setOnClickListener(v -> listener.onClick(v, expression));
        ibDownvote.setOnClickListener(v -> presenter.tryVote(false));
        ibUpvote.setOnClickListener(v -> presenter.tryVote(true));
        tvUsername.setOnClickListener( v -> redirectToUserPage(expression.getUser()));
        ibAudio.setOnClickListener(v -> presenter.playAudio());
    }

    @Override
    public void displayExpression(Expression expression) {
        tvDate.setText(expression.getCreatedAt());
        tvExpression.setText(expression.getWord());
        tvDefinition.setText(expression.getDefinition());
        tvScore.setText(String.valueOf(expression.getScore()));
        tvUsername.setText(expression.getUser() == null ? context.getString(R.string.deleted) : expression.getUser().getUsername());
        ivLocaleFlag.setImageDrawable(context.getDrawable(expression.getFlagImage()));
        if(expression.getHasAudio()) {
            ibAudio.setVisibility(View.VISIBLE);
        }
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
    public void displayUserVote(boolean voteType) {
        ibUpvote.setImageResource( voteType == false ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_up_green);
        ibDownvote.setImageResource( voteType == false ? R.drawable.ic_arrow_down_red : R.drawable.ic_arrow_down);
        ibDownvote.setEnabled(voteType);
        ibUpvote.setEnabled(voteType == true ? false : true);
    }

    @Override
    public void searchWithTag(String tag) {
        MainActivity activity = (MainActivity) context;
        Fragment myFragment = SearchFragment.newInstance(tag);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void redirectToUserPage(User user) {
        if(user != null) {
            MainActivity activity = (MainActivity) context;
            Fragment myFragment = UserFragment.newInstance(user);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
        } else {
            showToast(context.getString(R.string.user_deleted));
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
