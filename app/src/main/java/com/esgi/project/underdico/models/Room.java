package com.esgi.project.underdico.models;

import android.util.Pair;

import com.esgi.project.underdico.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Room implements Serializable {
    private Date createdAt;
    private Date updatedAt;
    private String id;
    private String name;
    private int maxPlayers;
    private boolean isPrivate;
    private boolean isRanked;
    private String status;
    private String[] playersIds;
    private String ownerId;
    private String locale;
    private String[] rounds;

    public Room(String id) {
        this.id = id;
    }

    public Room(String name, int maxPlayers, String locale, boolean isPrivate) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.locale = locale;
        this.isPrivate = isPrivate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String[] getPlayersIds() {
        return playersIds;
    }

    public Integer getFlagImage() {
        for (Pair<String, Integer> flag : Constants.flags) {
            if(flag.first.equals(locale)) {
                return flag.second;
            }
        }
        return 0;
    }
}
