package com.esgi.project.underdico.views.sampledata;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.R;


public class TopFragment extends Fragment implements TopView {

    enum TabLayoutPosition {
        DAY, WEEK, ALLTIME
    }

    TabLayout tabLayout;

    public TopFragment() {
        // Required empty public constructor
    }

    public static TopFragment newInstance() {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void assignViews() {
        tabLayout = getView().findViewById(R.id.tabLayout);
    }

    @Override
    public void setListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if(tabPosition == TabLayoutPosition.DAY.ordinal())
                {

                } else if(tabPosition == TabLayoutPosition.WEEK.ordinal()) {

                } else if (tabPosition == TabLayoutPosition.ALLTIME.ordinal()) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void showError(String error) {

    }
}
