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
    User user;

    public MainPresenter(MainView view, Context context) {
        this.view = view;
        this.context = context;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
        view.displayUserInformation();
        setLanguage(Session.getCurrentUser().getLanguage());

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
