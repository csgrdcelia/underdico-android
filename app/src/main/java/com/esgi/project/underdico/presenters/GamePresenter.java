package com.esgi.project.underdico.presenters;

import android.app.Activity;
import android.content.Context;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.GameSocket;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.game.GameView;

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
        joinRoom();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
        view.displayRoomInformation(room);
        view.displayPlayers(players);

        if(room.isJustCreated()) {
            view.displayWaitingGame(room.wasCreatedBy(Session.getCurrentUser()));
        } else if (room.isStarted()) {
            view.displayStartedGame();
        } else {
            view.displayTerminatedGame();
        }
    }

    public void showRoomIsStarted() {
        view.displayStartedGame();
    }

    public void displayNewUser(User player) {
        if(players.contains(player))
            return;
        players.add(player);
        view.displayPlayers(players);
    }

    public void displayNewRound(Expression expression) {
        view.displayRound(expression);
    }

    public void sendAnswer(String answer) {
        gameSocket.play(answer);
    }

    public void showProposalResult(boolean isCorrect, String playerId) {
        User sender = players.stream().filter(user -> user.getId().equals(playerId)).findFirst().get();
        view.displayProposalResult(isCorrect, sender.getUsername());
    }

    public void removeUser(User user) {
        if(players.contains(user)) {
            players.remove(user);
            view.displayPlayers(players);
        }
    }

    public void startRoom() {
        gameSocket.startRoom();
        view.displayStartedGame();
    }

    public void joinRoom() {
        gameSocket.connect();
        gameSocket.joinRoom();
    }

    public void leaveRoom() {
        gameSocket.leaveRoom();
    }

    public void disconnect() {
        gameSocket.disconnect();
    }
}
