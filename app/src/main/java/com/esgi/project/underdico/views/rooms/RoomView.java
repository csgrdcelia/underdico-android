package com.esgi.project.underdico.views.rooms;

import com.esgi.project.underdico.models.Room;

import java.util.List;

public interface RoomView {
    void assignViews();
    void setListeners();
    void displayRoom(Room room);
    void redirectToGame(String roomId);
}
