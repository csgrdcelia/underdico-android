package com.esgi.project.underdico.views.main;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.User;

public interface MainView {
    void assignViews();
    void setListeners();
    void displayUserInformation(User user);
    void displayRandomExpression(Expression expression);
    void showError(String error);
    void redirectToLoginPage();
}
