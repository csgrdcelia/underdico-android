package com.esgi.project.underdico.views.game;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.views.BaseView;

import java.util.List;

public interface GameView extends BaseView {
    void displayRoomInformation(Room room);
    void displayPlayers(List<User> users);
}
