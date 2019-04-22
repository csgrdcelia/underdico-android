package com.esgi.project.underdico;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.esgi.project.underdico.models.Expression;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ImageButton closeDayExpression;
    ConstraintLayout dayExpressionLayout;
    ConstraintLayout dayExpressionInnerLayout;
    RecyclerView expressionsRecyclerView;

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

        assignViews();
        configureRecyclerView();
        setOnClickListeners();
        //updateFragment(R.id.dayExpressionInnerLayout, ExpressionFragment.newInstance(Expression.getExpressionOfTheDay())); //TODO: use adapter ?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    public void assignViews() {
        closeDayExpression = getView().findViewById(R.id.closeDayExpressionButton);
        dayExpressionLayout = getView().findViewById(R.id.dayExpressionLayout);
        dayExpressionInnerLayout = getView().findViewById(R.id.dayExpressionInnerLayout);
        expressionsRecyclerView = getView().findViewById(R.id.rcExpressions);
    }

    private void configureRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        ExpressionClickListener expressionClickListener = (view, expression) -> {
            MainActivity activity = (MainActivity)view.getContext();
            Fragment fragment = ExpressionFragment.newInstance((Expression)expression);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
        };

        expressionsRecyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),1));
        expressionsRecyclerView.setAdapter(new ExpressionAdapter(Expression.getExpressionsList(), expressionClickListener, getContext()));
        expressionsRecyclerView.addItemDecoration(dividerItemDecoration);

        expressionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 50 && dayExpressionLayout.getVisibility() == View.VISIBLE) {
                    hideDayExpression();
                }
            }
        });
    }

    public void setOnClickListeners() {
        closeDayExpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOrDisplayDayExpression();
            }
        });
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
        dayExpressionInnerLayout.setVisibility(View.GONE);
    }

    private void displayDayExpression() {
        closeDayExpression.setImageResource(R.drawable.ic_hide);
        dayExpressionInnerLayout.setVisibility(View.VISIBLE);
    }

    private void updateFragment(int res, Fragment fragmentToGive) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(res, fragmentToGive);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }
}
