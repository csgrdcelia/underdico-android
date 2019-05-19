package com.esgi.project.underdico.views.home;

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

import com.esgi.project.underdico.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.views.expression.ExpressionAdapter;
import com.esgi.project.underdico.views.expression.ExpressionClickListener;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.presenters.HomePresenter;
import com.esgi.project.underdico.models.Expression;

import java.util.List;


public class HomeFragment extends Fragment implements HomeView {
    HomePresenter presenter;

    // UI references
    ImageButton closeDayExpression;
    ConstraintLayout dayExpressionLayout;
    ConstraintLayout dayExpressionInnerLayout;
    RecyclerView expressionsRecyclerView;
    RecyclerView dayExpressionRecyclerView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void affectUIReferences() {
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
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        ExpressionClickListener expressionClickListener = (view, expression) -> displayDetailedExpression(view, expression);

        expressionsRecyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),1));
        expressionsRecyclerView.setAdapter(new ExpressionAdapter(expressions, expressionClickListener, getContext()));
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
    public void displayExpressionOfTheDay(List<Expression> expression) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        ExpressionClickListener expressionClickListener = (view, expressionClicked) -> displayDetailedExpression(view, expressionClicked);

        dayExpressionRecyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),1));
        dayExpressionRecyclerView.setAdapter(new ExpressionAdapter(expression, expressionClickListener, getContext()));
        dayExpressionRecyclerView.addItemDecoration(dividerItemDecoration);
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