package com.esgi.project.underdico.presenters;

import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.user.UserView;

public class UserPresenter {
    UserView view;
    User user;

    public UserPresenter(UserView view, User user) {
        this.view = view;
        this.user = user;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
        view.displayUserInformation(user);
        view.allowModification(user.getId().equals(Session.getCurrentToken().getUserId()));
    }

}
