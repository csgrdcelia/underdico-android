package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.LanguageManager;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.user.UserView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter {
    UserView view;
    User user;
    Context context;

    public UserPresenter(UserView view, User user, Context context) {
        this.view = view;
        this.user = user;
        this.context = context;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
        view.displayUserInformation(user);
        view.allowModification(user.getId().equals(Session.getCurrentToken().getUserId()));
    }

    public void processModifications(String language) {
        if (!language.equals(user.getLocale())) {
            new LanguageManager(context).set(language);
            user.setLocale(language);
            updateUser();
        }
        view.displayModificationView(false);
    }

    private void updateUser() {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.updateUser(Session.getCurrentToken().getValue(), user.getId(), user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.showProgress(false);
                if (response.isSuccessful()) {
                    Session.setCurrentUser(user);
                    view.refresh();
                } else {
                    view.showError(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showProgress(false);
                view.showError(context.getString(R.string.error));
            }
        });
    }

}
