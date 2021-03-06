package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
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
        String json = "{ \"$or\" : [ { \"name\": \"" + search + "\"  }, { \"tags\": \"" + search + "\" } ] }";

        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<List<Expression>> call = service.getExpressionsWithFilter(Session.getCurrentToken().getValue(), json);
        call.enqueue(new Callback<List<Expression>>() {
            @Override
            public void onResponse(Call<List<Expression>> call, Response<List<Expression>> response) {
                if (response.isSuccessful())
                    if (response.body() != null)
                        view.displaySearchResult(response.body());
                    else
                        view.showToast(context.getString(R.string.expression_error));
            }

            @Override
            public void onFailure(Call<List<Expression>> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showToast(context.getString(R.string.expression_error));
            }
        });
    }
}
