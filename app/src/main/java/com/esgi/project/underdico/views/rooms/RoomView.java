package com.esgi.project.underdico.views.rooms;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.BaseView;

public interface RoomView extends BaseView {
    void displayRoom(Room room);
    void redirectToGame(Room room);
}
