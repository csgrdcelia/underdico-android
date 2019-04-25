package com.esgi.project.underdico;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.esgi.project.underdico.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final String SEARCH_ARG = "SEARCH";
    private static final String EXPRESSIONS_LIST_ARG = "EXPRESSIONS LIST";

    String search;
    ArrayList<Expression> expressions;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String search, List<Expression> expressions) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_ARG, search);
        args.putSerializable(EXPRESSIONS_LIST_ARG, (ArrayList<Expression>)expressions);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null) {
            search = this.getArguments().getString(SEARCH_ARG);
            expressions = (ArrayList<Expression>) this.getArguments().getSerializable(EXPRESSIONS_LIST_ARG);
        } else {
            Toast.makeText(getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show(); //TODO: langue
            goHome();
        }
    }

    public void goHome() {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = HomeFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }
}
