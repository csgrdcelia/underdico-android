package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.views.game.GameView;

public class GamePresenter {
    private GameView view;
    private Context context;

    public GamePresenter(Context context, GameView view) {
        this.context = context;
        this.view = view;

        initializeView();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
    }


}
