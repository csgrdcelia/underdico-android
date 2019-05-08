package com.esgi.project.underdico.utils;

import com.esgi.project.underdico.models.User;

public class Session {

    private static User currentUser = null;

    private Session() {}

    public static void setCurrentUser(User user) {
        //TODO: shared prefs
        currentUser = user;
    }

    public static void reset() {
        //TODO : shared prefs
        setCurrentUser(null);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
