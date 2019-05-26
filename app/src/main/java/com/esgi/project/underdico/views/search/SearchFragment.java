package com.esgi.project.underdico.views.search;

import android.content.Context;
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

import com.esgi.project.underdico.presenters.SearchPresenter;
import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.views.expression.ExpressionAdapter;
import com.esgi.project.underdico.views.expression.ExpressionClickListener;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.views.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView {

    private static final String SEARCH_ARG = "SEARCH";

    SearchPresenter presenter;
    RecyclerView rvResults;
    Context context;


    public SearchFragment() {
        // Required empty public constructor
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null) {
            String search = this.getArguments().getString(SEARCH_ARG);
            presenter = new SearchPresenter(getContext(), this, search);
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();
            goHome();
        }
    }

    @Override
    public void assignViews() {
        rvResults = getView().findViewById(R.id.rvResults);
    }

    @Override
    public void setListeners() { }

    @Override
    public void displaySearchResult(List<Expression> expressions) {
        configureRecyclerView(expressions);
    }

    private void configureRecyclerView(List<Expression> expressions) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(context, R.drawable.divider_10dp)
        );

        ExpressionClickListener expressionClickListener = (view, expression) -> displayDetailedExpression(view, expression);

        rvResults.setLayoutManager(new GridLayoutManager(context,1));
        rvResults.setAdapter(new ExpressionAdapter(expressions, expressionClickListener, context, null));
        rvResults.addItemDecoration(dividerItemDecoration);
    }

    private void displayDetailedExpression(View view, Object expression) {
        MainActivity activity = (MainActivity)view.getContext();
        Fragment fragment = ExpressionFragment.newInstance((Expression)expression);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goHome() {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = HomeFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }
}
