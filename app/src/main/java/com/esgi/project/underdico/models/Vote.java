package com.esgi.project.underdico.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vote implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private boolean type;
    @SerializedName("accountId")
    private String userId;
    @SerializedName("wordId")
    private String expressionId;

    public Vote(boolean type, User user, Expression expression) {
        this.type = type;
        this.userId = user.getId();
        this.expressionId = expression.getId();
    }

    public Vote(boolean score) {
        this.type = score;
    }

    public Vote(User user, boolean score){
        this.userId = user.getId();
        this.type = score;
    }

    public String getId() {
        return id;
    }

    public boolean getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }
}

