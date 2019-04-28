package com.esgi.project.underdico.models;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Expression implements Serializable {
    private String label;
    private String definition;
    private User user;
    private String[] tags;
    private List<Vote> votes;
    private Date createdAt;
    private Date updatedAt;


    public Expression(String label, String definition, User user, String[] tags, List<Vote> votes, Date createdAt, Date updatedAt) {
        this.label = label;
        this.definition = definition;
        this.user = user;
        this.tags = tags;
        this.votes = votes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getScore()
    {
        int score = 0;
        if (votes != null) {
            for (Vote vote : votes)
                score += vote.getScore();
        }
        return score;
    }

    public Vote getVote(User user) {
        if (votes != null) {
            for (Vote vote : votes)
                if (vote.getUser() == user)
                    return vote;
        }
        return null;
    }

    public String getTags() {
        if(tags != null)
            return TextUtils.join(" ", tags);
        else
            return "";
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

    public String getLabel() {
        return label;
    }

    public String getDefinition() {
        return definition;
    }

    public User getUser() {
        return user;
    }

    //TODO: get real expression of the day
    public static Expression getExpressionOfTheDay() {
        return new Expression("Expression du jour", "Ceci est la définition de l'expression du jour", new User("user"), null, null, null, null);
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
                new User("user1"),
                new String[] {"tag1", "tag2", "tag3"},
                new ArrayList<Vote>() {{ add(new Vote(-1)); add(new Vote(1));  add(new Vote(1));}},
                new GregorianCalendar(2019, 0,1).getTime(),
                new GregorianCalendar(2019, 0,2).getTime()));
        expressions.add(new Expression(
                "TRUC",
                "Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(1)); add(new Vote(1));  add(new Vote(1));}},
                new GregorianCalendar(2019, 0,15).getTime(),
                new GregorianCalendar(2019, 0,1).getTime()));
        expressions.add(new Expression(
                "ARGOT",
                "Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(-1)); add(new Vote(-1));  add(new Vote(1));}},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 0,3).getTime()));
        expressions.add(new Expression(
                "AFROTRAP",
                "Ceci est une définition",
                new User("user1"),
                new String[] {"tag1", "tag2", "tag3"},
                new ArrayList<Vote>() {{ add(new Vote(-1)); add(new Vote(-1));  add(new Vote(-1));}},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 5,1).getTime()));
        expressions.add(new Expression(
                "TRUC",
                "Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(-1)); add(new Vote(-1));  add(new Vote(1));}},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 5,1).getTime()));
        expressions.add(new Expression(
                "ARGOT",
                "Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new ArrayList<Vote>() {{ add(new Vote(-1)); add(new Vote(-1));  add(new Vote(1));}},
                new GregorianCalendar(2019, 1,1).getTime(),
                new GregorianCalendar(2019, 6,1).getTime()));

        return expressions;
    }



}
