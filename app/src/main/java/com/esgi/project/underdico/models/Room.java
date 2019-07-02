package com.esgi.project.underdico.models;

import android.content.Context;
import android.util.Pair;

import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.Session;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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

    /*public List<User> getPlayers(Context context) {
        List<User> players = new ArrayList<>();

        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);

        for (String userId : playersIds) {
            try {
                Call<User> call = service.getUser(Session.getCurrentToken().getValue(), userId);
                Response<User> userResponse = call.execute();
                User user = userResponse.body();
                players.add(user);
            } catch(IOException e) { }
        }
        return players;
    }*/

    public List<User> getPlayers() {
        List<User> players = new ArrayList<>();
        for (String userId : playersIds) {
            players.add(new User(userId, "user"));
        }
        return players;
    }
}
