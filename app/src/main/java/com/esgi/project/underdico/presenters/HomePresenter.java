package com.esgi.project.underdico.presenters;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.home.HomeView;

import java.util.ArrayList;
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

    public List<Expression> getDayExpression() {
        List<Expression> expression = new ArrayList<>();
        expression.add(Expression.getExpressionOfTheDay());
        return expression;
    }
}
