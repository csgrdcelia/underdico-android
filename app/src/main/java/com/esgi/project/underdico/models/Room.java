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
