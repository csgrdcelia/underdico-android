package com.esgi.project.underdico.views.newroom;

public interface NewRoomView {
    void assignViews();
    void setListeners();
    void redirectToRoomList();
    void showError(String error);
}
