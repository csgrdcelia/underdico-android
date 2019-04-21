package com.esgi.project.underdico;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


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
        setOnClickListeners();
        updateFragment(R.id.dayExpressionInnerLayout, ExpressionFragment.newInstance());
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
        expressionsRecyclerView.setLayoutManager(new GridLayoutManager(getView().getContext(),1));
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
        /*if (dayExpressionInnerLayout.getVisibility() == View.VISIBLE)
            dayExpressionInnerLayout.setVisibility(View.GONE);
        else
            dayExpressionInnerLayout.setVisibility(View.VISIBLE);*/
        dayExpressionLayout.setVisibility(View.GONE);
    }

    private void updateFragment(int res, Fragment fragmentToGive) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(res, fragmentToGive);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }
}
