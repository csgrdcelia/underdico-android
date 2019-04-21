package com.esgi.project.underdico.models;

public class Vote {
    private User user;
    private int score;

    public Vote(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

