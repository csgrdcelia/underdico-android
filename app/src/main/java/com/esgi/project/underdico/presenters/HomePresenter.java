package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.home.HomeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    HomeView view;
    Context context;

    public HomePresenter(HomeView view, Context context) {
        this.view = view;
        this.context = context;

        initialize();
    }

    private void initialize() {
        view.affectUIReferences();
        view.setListeners();
        displayExpressionOfTheDay();
        displayExpressions();
    }

    private void displayExpressionOfTheDay() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Expression> call = service.getExpressionOfTheDay();
        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if(response.isSuccessful())
                    if(response.body() != null)
                        view.displayExpressionOfTheDay(response.body());
                    else
                        view.showError(context.getString(R.string.expression_error));
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {
                view.showError(context.getString(R.string.expression_error));
            }
        });

    }

    private void displayExpressions() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<List<Expression>> call = service.getExpressions(Session.getCurrent().getValue());
        call.enqueue(new Callback<List<Expression>>() {
            @Override
            public void onResponse(Call<List<Expression>> call, Response<List<Expression>> response) {
                if(response.isSuccessful())
                    if(response.body() != null)
                        view.displayExpressions(response.body());
                else
                    view.showError(context.getString(R.string.expression_error));
            }

            @Override
            public void onFailure(Call<List<Expression>> call, Throwable t) {
                view.showError(context.getString(R.string.expression_error));
            }
        });
    }
}
