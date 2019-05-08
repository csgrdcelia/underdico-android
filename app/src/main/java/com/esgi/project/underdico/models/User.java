package com.esgi.project.underdico.models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
