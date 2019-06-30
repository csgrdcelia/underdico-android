package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.game.GameView;

public class GamePresenter {
    private Context context;
    private GameView view;
    private Room room;

    public GamePresenter(Context context, GameView view, Room room) {
        this.context = context;
        this.view = view;
        this.room = room;

        initializeView();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
        view.displayRoomInformation(room);
    }


}
