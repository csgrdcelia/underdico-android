package com.esgi.project.underdico.models;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Expression {
    private String label;
    private String definition;
    private User user;
    private String[] tags;
    private Vote[] votes;
    private Date createdAt;
    private Date updatedAt;


    public Expression(String label, String definition, User user, String[] tags, Vote[] votes, Date createdAt, Date updatedAt) {
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

        for(Vote vote : votes)
            score += vote.getScore();

        return score;
    }

    public String getTags() {
        return TextUtils.join(" ", tags);
    }

    public String getCreatedAt() {
        Format df = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE);
        return df.format(createdAt);
        //return createdAt.toString();
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

    public static List<Expression> getTestExpressionsList() {
        List<Expression> expressions = new ArrayList<Expression>();
        expressions.add(new Expression(
                "AFROTRAP",
                "Ceci est une définition",
                new User("user1"),
                new String[] {"tag1", "tag2", "tag3"},
                new Vote[] { new Vote(-1), new Vote(1), new Vote(1)},
                new GregorianCalendar(2019, 0,1).getTime(),
                new GregorianCalendar(2019, 0,2).getTime()));
        expressions.add(new Expression(
                "TRUC",
                "Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new Vote[] { new Vote(-1), new Vote(1), new Vote(1), new Vote(1)},
                new GregorianCalendar(2019, 0,15).getTime(),
                new GregorianCalendar(2019, 0,1).getTime()));
        expressions.add(new Expression(
                "ARGOT",
                "Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new Vote[] { new Vote(-1), new Vote(1), new Vote(1), new Vote(1)},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 0,3).getTime()));
        expressions.add(new Expression(
                "AFROTRAP",
                "Ceci est une définition",
                new User("user1"),
                new String[] {"tag1", "tag2", "tag3"},
                new Vote[] { new Vote(-1), new Vote(1), new Vote(1)},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 5,1).getTime()));
        expressions.add(new Expression(
                "TRUC",
                "Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. Ceci est une autre définition. Elle est très bien. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new Vote[] { new Vote(-1), new Vote(1), new Vote(1), new Vote(1)},
                new GregorianCalendar(2019, 2,1).getTime(),
                new GregorianCalendar(2019, 5,1).getTime()));
        expressions.add(new Expression(
                "ARGOT",
                "Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. Ceci est une autre définition. ",
                new User("user2"),
                new String[] {"tag1", "tag2", "tag3", "tag5"},
                new Vote[] { new Vote(-1), new Vote(1), new Vote(1), new Vote(1)},
                new GregorianCalendar(2019, 1,1).getTime(),
                new GregorianCalendar(2019, 6,1).getTime()));

        return expressions;
    }



}