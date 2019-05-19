package com.esgi.project.underdico.views.register;

public interface RegisterView {
    void askUserToFillFields();
    void assignViews();
    void setListeners();
    void checkUsernameValidityInRealTime();
    void checkEmailValidityInRealTime();
    void checkPasswordValidityInRealTime();
    void checkPasswordsMatchInRealTime();
    boolean hasInvalidFields();
    void showRegisterSuccess();
    void showLoginView(String username);
    void showError(String error);
}
