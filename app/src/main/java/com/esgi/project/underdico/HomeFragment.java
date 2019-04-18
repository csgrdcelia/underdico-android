package com.esgi.project.underdico;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
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
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assignViews();
        setOnClickListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    public void assignViews() {
        closeDayExpression = getView().findViewById(R.id.closeDayExpressionButton);
        dayExpressionLayout = getView().findViewById(R.id.dayExpressionLayout);
    }

    public void setOnClickListeners() {
        closeDayExpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDayExpression();
            }
        });
    }

    public void hideDayExpression() {
        dayExpressionLayout.setVisibility(View.GONE);
    }
}
