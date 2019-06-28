package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.splash.SplashView;

public class SplashPresenter {
    Context context;
    SplashView view;

    public SplashPresenter(SplashView view, Context context) {
        this.context = context;
        this.view = view;

        redirect();
    }

    private void redirect() {
        Token token = Session.getFromSharedPref(context);
        if(token != null && token.isValid()) {
            Session.setCurrentToken(token);
            Session.callUserInformation(context, view);
        } else {
            view.redirectToLoginPage();
        }
    }
}
