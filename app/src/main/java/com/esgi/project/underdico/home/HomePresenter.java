package com.esgi.project.underdico.home;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

public class HomePresenter {
    HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    public List<Expression> getExpressionsList() {
        //TODO: call API
        return Expression.getExpressionsList();
    }
}
