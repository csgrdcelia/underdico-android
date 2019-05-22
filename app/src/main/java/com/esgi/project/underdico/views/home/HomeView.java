package com.esgi.project.underdico.views.home;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

public interface HomeView {
    void assignViews();
    void setListeners();
    void displayExpressions(List<Expression> expressions);
    void displayExpressionOfTheDay(Expression expression);
    boolean isExpressionOfTheDay(Expression expression);
    void showError(String error);
}
