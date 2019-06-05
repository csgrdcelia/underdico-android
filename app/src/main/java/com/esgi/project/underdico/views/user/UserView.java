package com.esgi.project.underdico.views.user;

import android.graphics.Bitmap;

import com.esgi.project.underdico.models.User;

public interface UserView {
    void assignViews();
    void setListeners();
    void displayUserInformation(User user);
    void allowModification(boolean allow);
    void displayModificationView(boolean display);
    void showProgress(boolean show);
    void setProfilePicture(Bitmap image);
    void showError(String error);
    void redirectToHome();
    void refresh();

}
