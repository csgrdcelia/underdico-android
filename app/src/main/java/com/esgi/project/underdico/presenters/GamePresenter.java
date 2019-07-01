package com.esgi.project.underdico.presenters;

import android.app.Activity;
import android.content.Context;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.services.GameSocket;
import com.esgi.project.underdico.views.game.GameView;

public class GamePresenter {
    private Context context;
    Activity activity;
    private GameView view;
    private Room room;

    public GamePresenter(Context context, Activity activity, GameView view, Room room) {
        this.context = context;
        this.activity = activity;
        this.view = view;
        this.room = room;

        initializeView();
        beginGame();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
        view.displayRoomInformation(room);
    }

    private void beginGame() {
        GameSocket socket = new GameSocket(view, activity, room);
        socket.connect();
        socket.joinRoom();
    }


}
