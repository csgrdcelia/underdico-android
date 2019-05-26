package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.LanguageManager;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.main.MainView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    MainView view;
    Context context;

    public MainPresenter(MainView view, Context context) {
        this.view = view;
        this.context = context;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
        callUserInformation();
    }

    private void callUserInformation() {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.getUser(Session.getCurrentToken().getValue(), Session.getCurrentToken().getUserId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() || response.body() != null) {
                    view.displayUserInformation(response.body());
                    setLanguage(response.body().getLanguage());
                } else if (response.code() == Constants.HTTP_UNAUTHORIZED) {
                    view.redirectToLoginPage();
                } else {
                    view.showError(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.showError(context.getString(R.string.network_error));
            }
        });
    }

    public void callRandomExpression() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Expression> call = service.getRandomExpression(Session.getCurrentToken().getValue());
        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if(response.isSuccessful())
                    if(response.body() != null)
                        view.displayRandomExpression(response.body());
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLanguage(String code) {
        LanguageManager manager = new LanguageManager(context);
        boolean changed = manager.set(code);
        if(changed) {
            view.refresh();
        }

    }

}
