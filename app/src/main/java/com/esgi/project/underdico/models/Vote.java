package com.esgi.project.underdico.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Vote implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("value")
    private boolean type;
    @SerializedName("userId")
    private String userId;
    @SerializedName("wordId")
    private String expressionId;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;

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

