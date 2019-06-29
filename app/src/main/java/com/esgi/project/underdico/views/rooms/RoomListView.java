package com.esgi.project.underdico.views.rooms;

import com.esgi.project.underdico.models.Room;

import java.util.List;

public interface RoomListView {
    void assignViews();
    void setListeners();
    void displayRooms(List<Room> rooms);
    void redirectToRoomCreation();
    void showError(String error);
}
