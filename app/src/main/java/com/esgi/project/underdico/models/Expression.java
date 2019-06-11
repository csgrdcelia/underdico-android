package com.esgi.project.underdico.models;

import android.text.TextUtils;
import android.util.Pair;

import com.esgi.project.underdico.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Expression implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String label;
    @SerializedName("definition")
    private String definition;
    @SerializedName("tags")
    private String[] tags;
    @SerializedName("votes")
    private List<Vote> votes;
    @SerializedName("score")
    private int score;
    @SerializedName("user")
    private User user;
    @SerializedName("userUpVoted")
    private boolean userUpvoted;
    @SerializedName("userDownVoted")
    private boolean userDownvoted;
    @SerializedName("userVoteId")
    private String userVoteId;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;
    @SerializedName("locale")
    private String locale;

    public Expression() { }

    public Expression(String label, String definition, String[] tags, String locale) {
        this.label = label;
        this.definition = definition;
        this.tags = tags;
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getDefinition() {
        return definition;
    }

    public boolean userUpvoted() {
        return userUpvoted;
    }

    public boolean userDownvoted() {
        return userDownvoted;
    }

    public int getScore()
    {
        return score;
    }

    public String getUserVoteId() {
        return userVoteId;
    }

    public Expression(String label, String definition, User user, String[] tags, List<Vote> votes, Date createdAt, Date updatedAt) {
        this.label = label;
        this.definition = definition;
        this.user = user;
        this.tags = tags;
        this.votes = votes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Vote getVote(User user) {
        if (votes != null) {
            for (Vote vote : votes)
                if (vote.getUserId().equals(user.getId()))
                    return vote;
        }
        return null;
    }

    public String[] getTagArray() {
        return tags;
    }

    public String getCreatedAt() {
        if(createdAt != null) {
            Format df = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE);
            return df.format(createdAt);
        }
        return "";
    }

    public User getUser() {
        return user;
    }

    public String getLocale() {
        return locale;
    }

    public Integer getFlagImage() {
        for (Pair<String, Integer> flag : Constants.flags) {
            if(flag.first.equals(getLocale())) {
                return flag.second;
            }
        }
        return 0;
    }
}
