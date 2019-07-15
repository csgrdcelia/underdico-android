package com.esgi.project.underdico.views.newroom;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.views.BaseView;

public interface NewRoomView extends BaseView {
    void displayPrivateCode(String code);
    void redirectToGame(Room room);
}
