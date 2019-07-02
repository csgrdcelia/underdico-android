package com.esgi.project.underdico.presenters;

import android.app.Activity;
import android.content.Context;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.GameSocket;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.game.GameView;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter {
    private Context context;
    private Activity activity;
    private GameView view;
    private Room room;
    List<User> players;

    private GameSocket gameSocket;

    public GamePresenter(Context context, Activity activity, GameView view, Room room) {
        this.context = context;
        this.activity = activity;
        this.view = view;
        this.room = room;
        this.players = room.getPlayers();
        this.gameSocket = new GameSocket(activity, this, room, Session.getCurrentToken().getValue());

        initializeView();
        beginGame();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
        view.displayRoomInformation(room);
        view.displayPlayers(players);
    }

    public void displayNewUser(User player) {
        if(players.contains(player))
            return;
        players.add(player);
        view.displayPlayers(players);
    }

    public void removeUser(User user) {

    }

    private void beginGame() {
        gameSocket.connect();
    }

    public void leaveRoom() {
        gameSocket.leaveRoom();
    }


}
