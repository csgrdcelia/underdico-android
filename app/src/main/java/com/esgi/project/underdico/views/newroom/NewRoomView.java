package com.esgi.project.underdico.views.newroom;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.BaseView;

public interface NewRoomView extends BaseView {
    void redirectToGame(Room room);
}
