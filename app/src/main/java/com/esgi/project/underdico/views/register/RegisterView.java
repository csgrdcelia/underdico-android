package com.esgi.project.underdico.views.register;

import com.esgi.project.underdico.views.BaseView;

public interface RegisterView extends BaseView {
    void askUserToFillFields();
    void checkUsernameValidityInRealTime();
    void checkEmailValidityInRealTime();
    void checkPasswordValidityInRealTime();
    void checkPasswordsMatchInRealTime();
    boolean hasInvalidFields();
    void showRegisterSuccess();
    void showLoginView(String username);
}
