package com.esgi.project.underdico.models;

import android.util.Pair;

import com.esgi.project.underdico.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Expression implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String word;
    @SerializedName("definition")
    private String definition;
    @SerializedName("example")
    private String example;
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
    @SerializedName("hasAudio")
    private boolean hasAudio;

    public Expression() { }

    public Expression(String word, String definition, String example, String[] tags, String locale) {
        this.word = word;
        this.definition = definition;
        this.example = example;
        this.tags = tags;
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
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

    public Expression(String word, String definition, User user, String[] tags, List<Vote> votes, Date createdAt, Date updatedAt) {
        this.word = word;
        this.definition = definition;
        this.user = user;
        this.tags = tags;
        this.votes = votes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getHiddenWord() {
        String hiddenWord = "";
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (Character.isLetter(currentChar))
                hiddenWord += "_";
            else if (Character.isSpaceChar(currentChar))
                hiddenWord += " ";
            else
                hiddenWord += currentChar;
            hiddenWord += " ";
        }
        return hiddenWord.substring(0, hiddenWord.length() - 1);
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

    public String getExample() {
        return example;
    }

    public boolean getHasAudio() {
        return hasAudio;
    }
}
