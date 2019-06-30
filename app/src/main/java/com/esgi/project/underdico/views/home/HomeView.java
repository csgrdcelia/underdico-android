package com.esgi.project.underdico.views.home;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.BaseView;

import java.util.List;

public interface HomeView extends BaseView {
    void displayExpressions(List<Expression> expressions);
    void displayExpressionOfTheDay(Expression expression);
    boolean isExpressionOfTheDay(Expression expression);
    void showError(String error);
}
