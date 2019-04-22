package com.esgi.project.underdico.models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
