package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.views.main.MainView;

public class MainPresenter {
    MainView view;
    Context context;

    public MainPresenter(MainView view, Context context) {
        this.view = view;
        this.context = context;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
        view.displayUserInformation();
    }

}
