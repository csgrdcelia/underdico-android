package com.esgi.project.underdico.models;

import android.text.TextUtils;

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
    @SerializedName("accountId")
    private String userId;

    private Date createdAt;
    private Date updatedAt;


    public Expression() { }

    public Expression(String label, String definition, String[] tags, User user) {
        this.label = label;
        this.definition = definition;
        this.tags = tags;
        this.userId = user.getId();
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    private User user;

    public int getScore()
    {
        int score = 0;
        if (votes != null) {
            for (Vote vote : votes) {
                if(vote.getType())
                    score += 1;
                else
                    score -= 1;
            }
        }
        return score;
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
        } else {
            return "Pas de date de création..."; //TODO: réfléchir à l'affichage et penser aux différentes langues
        }
    }

    public void addVote(Vote vote) {
        if(votes == null)
            votes = new ArrayList<>();
        votes.add(vote);
    }

    public void removeVote(Vote vote) {
        if(votes != null && votes.contains(vote))
            votes.remove(vote);
    }

    public User getUser() {
        return user;
    }

    //TODO: get real expression of the day
    public static Expression getExpressionOfTheDay() {
        return new Expression("Expression du jour", "Ceci est la définition de l'expression du jour", new User("2", "user"), null, null, null, null);
    }

    public static Expression getRandomExpression() {
        List<Expression> expressions = getExpressionsList();
        return expressions.get(new Random().nextInt(expressions.size()));
    }

    //TODO: get real expressions list
    public static List<Expression> getExpressionsList() {
        List<Expression> expressions = new ArrayList<Expression>();
        expressions.add(new Expression(
                "AFROTRAP",
                "Ceci est une définition",
                new User("1", "user1"),
                new String[] {"tag1", "tag2", "tag3"},
                new ArrayList<Vote>() {{ add(new Vote(false)); add(new Vote(true));  add(new Vote(true));}},
                new GregorianCalendar(2019, 0,1).getTime(),
                new GregorianCalendar(2019, 0,2).getTime()));
        expressions.add(new Expression(
                "TRUC",
                "Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. ",
                new User("2", "user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(true)); add(new Vote(true));  add(new Vote(true));}},
                new GregorianCalendar(2019, 0,15).getTime(),
                new GregorianCalendar(2019, 0,1).getTime()));
        expressions.add(new Expression(
                "ARGOT",
                "Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. ",
                new User("2", "user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(false)); add(new Vote(false));  add(new Vote(true));}},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 0,3).getTime()));
        expressions.add(new Expression(
                "AFROTRAP",
                "Ceci est une définition",
                new User("2", "user1"),
                new String[] {"tag1", "tag2", "tag3"},
                new ArrayList<Vote>() {{ add(new Vote(false)); add(new Vote(false));  add(new Vote(false));}},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 5,1).getTime()));
        expressions.add(new Expression(
                "TRUC",
                "Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. ",
                new User("2", "user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(true)); add(new Vote(true)); add(new Vote(true)); add(new Vote(true));  add(new Vote(true));}},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 5,1).getTime()));
        expressions.add(new Expression(
                "ARGOT",
                "Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. ",
                new User("2", "user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(false)); add(new Vote(false));  add(new Vote(true));}},
                new GregorianCalendar(2019, 1,1).getTime(),
                new GregorianCalendar(2019, 6,1).getTime()));

        return expressions;
    }



}
