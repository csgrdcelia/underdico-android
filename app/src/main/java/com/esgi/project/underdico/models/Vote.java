package com.esgi.project.underdico.models;

import java.io.Serializable;

public class Vote implements Serializable {
    public enum Type {
        DOWN,
        UP
    }

    private User user;
    private int score;

    public Vote(int score) {
        this.score = score;
    }

    public Vote(User user, int score){
        this.user = user;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }
    public Type getType() {
        return score == -1 ? Type.DOWN : Type.UP;
    }
}

