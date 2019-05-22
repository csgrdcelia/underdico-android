package com.esgi.project.underdico.views.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.esgi.project.underdico.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.views.expression.ExpressionAdapter;
import com.esgi.project.underdico.views.expression.ExpressionClickListener;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.views.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final String SEARCH_ARG = "SEARCH";
    private static final String EXPRESSIONS_LIST_ARG = "EXPRESSIONS LIST";

    // UI references
    RecyclerView rvResults;

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

    public static SearchFragment newInstance(String search) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_ARG, search);
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
            assignViews();
            search = this.getArguments().getString(SEARCH_ARG);
            //expressions = (ArrayList<Expression>) this.getArguments().getSerializable(EXPRESSIONS_LIST_ARG);
            expressions = (ArrayList<Expression>)Expression.getExpressionsList();
            displaySearch();
        } else {
            Toast.makeText(getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show(); //TODO: langue
            goHome();
        }
    }

    private void assignViews() {
        rvResults = getView().findViewById(R.id.rvResults);
    }

    private void displaySearch() {
        displaySearchValueInSearchBar();
        configureRecyclerView();
    }

    private void displaySearchValueInSearchBar() {
        MainActivity activity = (MainActivity) getView().getContext();

    }

    private void configureRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        ExpressionClickListener expressionClickListener = (view, expression) -> displayDetailedExpression(view, expression);

        rvResults.setLayoutManager(new GridLayoutManager(getView().getContext(),1));
        rvResults.setAdapter(new ExpressionAdapter(expressions, expressionClickListener, getContext(), null));
        rvResults.addItemDecoration(dividerItemDecoration);
    }

    private void displayDetailedExpression(View view, Object expression) {
        MainActivity activity = (MainActivity)view.getContext();
        Fragment fragment = ExpressionFragment.newInstance((Expression)expression);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

    public void goHome() {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = HomeFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }
}
