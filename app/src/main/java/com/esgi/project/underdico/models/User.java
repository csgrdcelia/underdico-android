package com.esgi.project.underdico.models;

import android.content.Context;

import com.esgi.project.underdico.R;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    public enum Role {
        Admin,
        User
    }

    private String id;
    private String username;
    private String password;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private String role;
    private int karma;
    private String locale;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username, String email, String password, Role role, String locale) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = (role == Role.Admin) ? "Admin" : "User";
        this.locale = locale;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getRole(Context context) {
        if (role.equals("User"))
            return context.getString(R.string.user);
        else if (role.equals("Admin"))
            return context.getString(R.string.admin);
        else
            return context.getString(R.string.undefined);
    }

    public int getKarma() {
        return karma;
    }

    public String getLocale() {
        return locale;
    }
}
