package com.esgi.project.underdico.views.Top;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

public interface TopView {
    void assignViews();
    void setListeners();
    void displayExpressions(List<Expression> expressions);
    void showError(String error);
}
