package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.rooms.RoomView;

import java.util.Arrays;

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
        if(room.isFull() && !Arrays.asList(room.getPlayersIds()).contains(Session.getCurrentUser().getId())) {
            view.showToast(context.getString(R.string.room_full));
        } else {
            view.redirectToGame(room);
        }
    }
}
