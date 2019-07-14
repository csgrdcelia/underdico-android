package com.esgi.project.underdico.views.rooms;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.BaseView;

import java.util.List;

public interface RoomListView extends BaseView {
    void displayRooms(List<Room> rooms);
    void showSearchDialog();
    void showPrivateRoomDialog();
    void redirectToRoomCreation();
    void redirectToGame(Room room);
    void displaySearchResult(List<Room> rooms);
}
