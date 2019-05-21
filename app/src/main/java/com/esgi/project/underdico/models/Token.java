package com.esgi.project.underdico.models;

import java.util.Calendar;
import java.util.Date;

public class Token {
    private String token;
    private String userId;
    private int expiresIn;
    private Date createdAt;

    public boolean isValid() {
        Calendar limitDate = Calendar.getInstance();
        limitDate.setTime(createdAt);
        limitDate.add(Calendar.SECOND, expiresIn);
        return createdAt.before(limitDate.getTime());
    }

    public String getValue() {
        return "Bearer " + token;
    }
}
