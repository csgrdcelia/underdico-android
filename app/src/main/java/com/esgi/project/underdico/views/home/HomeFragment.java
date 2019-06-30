package com.esgi.project.underdico.views.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.views.expression.ExpressionAdapter;
import com.esgi.project.underdico.views.ViewClickListener;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.presenters.HomePresenter;
import com.esgi.project.underdico.models.Expression;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeView {
    HomePresenter presenter;

    // UI references
    ImageButton closeDayExpression;
    ConstraintLayout dayExpressionLayout;
    ConstraintLayout dayExpressionInnerLayout;
    RecyclerView expressionsRecyclerView;
    RecyclerView dayExpressionRecyclerView;

    Expression expressionOfTheDay;

    Context context;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new HomePresenter(this, getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void assignViews() {
        closeDayExpression = getView().findViewById(R.id.closeDayExpressionButton);
        dayExpressionLayout = getView().findViewById(R.id.dayExpressionLayout);
        dayExpressionInnerLayout = getView().findViewById(R.id.dayExpressionInnerLayout);
        expressionsRecyclerView = getView().findViewById(R.id.rcExpressions);
        dayExpressionRecyclerView = getView().findViewById(R.id.rcDayExpression);

    }

    @Override
    public void setListeners() {
        closeDayExpression.setOnClickListener(v -> hideOrDisplayDayExpression());
    }

    @Override
    public void displayExpressions(List<Expression> expressions) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(context, R.drawable.divider_10dp)
        );

        ViewClickListener expressionClickListener = (view, expression) -> displayDetailedExpression(view, expression);

        expressionsRecyclerView.setLayoutManager(new GridLayoutManager(context,1));
        expressionsRecyclerView.setAdapter(new ExpressionAdapter(expressions, expressionClickListener, getContext(), this));
        expressionsRecyclerView.addItemDecoration(dividerItemDecoration);

        expressionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 10 && dayExpressionLayout.getVisibility() == View.VISIBLE) {
                    hideDayExpression();
                }
            }
        });
    }

    @Override
    public void displayExpressionOfTheDay(Expression expression) {
        expressionOfTheDay = expression;
        List<Expression> expressions = new ArrayList<>();
        expressions.add(expression);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(context, R.drawable.divider_10dp)
        );

        ViewClickListener expressionClickListener = (view, expressionClicked) -> displayDetailedExpression(view, expressionClicked);

        dayExpressionRecyclerView.setLayoutManager(new GridLayoutManager(context,1));
        dayExpressionRecyclerView.setAdapter(new ExpressionAdapter(expressions, expressionClickListener, context, this));
        dayExpressionRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean isExpressionOfTheDay(Expression expression) {
        if(expressionOfTheDay == null)
            return false;
        return expressionOfTheDay.getId() == expression.getId();
    }

    private void displayDetailedExpression(View view, Object expression) {
        MainActivity activity = (MainActivity)view.getContext();
        Fragment fragment = ExpressionFragment.newInstance((Expression)expression);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

    public void hideOrDisplayDayExpression() {
        if (dayExpressionIsShown())
            hideDayExpression();
        else
            displayDayExpression();
    }

    private boolean dayExpressionIsShown() {
        return dayExpressionInnerLayout.getVisibility() == View.VISIBLE;
    }

    private void hideDayExpression() {
        closeDayExpression.setImageResource(R.drawable.ic_display);
        dayExpressionInnerLayout.animate().alpha(0.0f).setDuration(200);
        dayExpressionInnerLayout.setVisibility(View.GONE);
    }

    private void displayDayExpression() {
        closeDayExpression.setImageResource(R.drawable.ic_hide);
        dayExpressionInnerLayout.animate().alpha(1.0f);
        dayExpressionInnerLayout.setVisibility(View.VISIBLE);
    }



    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
