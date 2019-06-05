package com.esgi.project.underdico.views.main;

import android.graphics.Bitmap;

import com.esgi.project.underdico.models.Expression;

public interface MainView {
    void assignViews();
    void setListeners();
    void displayUserInformation();
    void setProfilePicture(Bitmap image);
    void displayRandomExpression(Expression expression);
    void showError(String error);
    void redirectToLoginPage();
    void refresh();
}
