package com.esgi.project.underdico.views.game;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.BaseView;

public interface GameView extends BaseView {
    void displayRoomInformation(Room room);
}
