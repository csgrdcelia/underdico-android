package com.esgi.project.underdico.models;

import java.io.Serializable;

public class Vote implements Serializable {
    private User user;
    private int score;

    public Vote(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

