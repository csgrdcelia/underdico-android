package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.views.register.RegisterView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    private RegisterView view;
    private Context context;

    public RegisterPresenter(RegisterView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void handleRegister(String username, String email, String password, String locale) {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || view.hasInvalidFields()) {
            view.askUserToFillFields();
        } else {
            User user = new User(username, email, password, User.Role.User, locale);
            tryRegister(user);
        }
    }

    private void tryRegister(User user) {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    view.showRegisterSuccess();
                    view.showLoginView(response.body().getUsername());
                } else if (response.code() == Constants.HTTP_CONFLICT) {
                    view.showToast(context.getString(R.string.existing_login_details));
                } else if (response.code() == Constants.HTTP_UNPROCESSABLE) {
                    view.showToast(context.getString(R.string.wrong_login_details));
                } else {
                    view.showToast(context.getString(R.string.register_fail));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.showToast(context.getString(R.string.register_fail));
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
            }
        });
    }
}
