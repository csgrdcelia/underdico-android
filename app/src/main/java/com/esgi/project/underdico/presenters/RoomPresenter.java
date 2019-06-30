package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.rooms.RoomView;

public class RoomPresenter {
    Context context;
    RoomView view;
    Room room;

    public RoomPresenter(Context context, RoomView view, Room room) {
        this.context = context;
        this.view = view;
        this.room = room;

        initializeView();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
        view.displayRoom(room);
    }

    public void roomIsClicked() {
        view.redirectToGame(room);
    }
}
