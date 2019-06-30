package com.esgi.project.underdico.views.user;

import android.graphics.Bitmap;

import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.views.BaseView;

public interface UserView extends BaseView {
    void displayUserInformation(User user);
    void allowModification(boolean allow);
    void displayModificationView(boolean display);
    void showProgress(boolean show);
    void setProfilePicture(Bitmap image);
    void redirectToHome();
    void refresh();
}
