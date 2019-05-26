package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.search.SearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {
    Context context;
    SearchView view;
    String search;

    public SearchPresenter(Context context, SearchView view, String search) {
        this.context = context;
        this.view = view;
        this.search = search;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();

        getSearchResult();
    }

    private void getSearchResult() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<List<Expression>> call = service.getExpressions(Session.getCurrentToken().getValue());
        call.enqueue(new Callback<List<Expression>>() {
            @Override
            public void onResponse(Call<List<Expression>> call, Response<List<Expression>> response) {
                if(response.isSuccessful())
                    if(response.body() != null)
                        view.displaySearchResult(response.body());
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
