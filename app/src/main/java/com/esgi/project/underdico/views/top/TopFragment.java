package com.esgi.project.underdico.views.top;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.presenters.TopPresenter;
import com.esgi.project.underdico.views.expression.ExpressionAdapter;
import com.esgi.project.underdico.views.expression.ExpressionClickListener;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.views.main.MainActivity;

import java.util.List;


public class TopFragment extends Fragment implements TopView {

    TopPresenter presenter;

    TabLayout tabLayout;
    RecyclerView topRecyclerView;
    TextView noExpressionTextView;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new TopPresenter(getContext(),this);
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
        topRecyclerView = getView().findViewById(R.id.rvTop);
        noExpressionTextView = getView().findViewById(R.id.tvNoExpressions);
        configureRecyclerView();
    }

    @Override
    public void setListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if(tabPosition == TopPresenter.TopType.DAY.ordinal())
                {
                    presenter.getExpressionTop(TopPresenter.TopType.DAY);
                } else if(tabPosition == TopPresenter.TopType.WEEK.ordinal()) {
                    presenter.getExpressionTop(TopPresenter.TopType.WEEK);
                } else if (tabPosition == TopPresenter.TopType.ALLTIME.ordinal()) {
                    presenter.getExpressionTop(TopPresenter.TopType.ALLTIME);
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
    public void displayExpressions(List<Expression> expressions) {
        if(expressions != null) {
            noExpressionTextView.setVisibility(View.GONE);
            ExpressionClickListener expressionClickListener = (view, expression) -> displayDetailedExpression(view, expression);
            topRecyclerView.setAdapter(new ExpressionAdapter(expressions, expressionClickListener, getContext(), null));
        }
        else {
            noExpressionTextView.setVisibility(View.VISIBLE);
        }
    }

    public void configureRecyclerView() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        topRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        topRecyclerView.addItemDecoration(dividerItemDecoration);
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
}
