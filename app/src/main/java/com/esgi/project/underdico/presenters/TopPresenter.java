package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.sampledata.TopView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopPresenter {
    Map<TopType, List<Expression>> topDictionary;

    public enum TopType {
        DAY, WEEK, ALLTIME
    }

    Context context;
    TopView view;

    public TopPresenter(Context context, TopView view) {
        this.context = context;
        this.view = view;
        initTopDictionary();
        initializeView();
    }

    private void initializeView() {
        getExpressionTop(TopType.DAY);
    }

    public void getExpressionTop(TopType type) {
        if(topDictionary.get(type) != null) {
            view.displayExpressions(topDictionary.get(type));
        } else {
            String json = ""; //TODO: set query with $gt & orderby

            ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
            Call<List<Expression>> call = service.getExpressionsWithFilter(Session.getCurrentToken().getValue(), json);
            call.enqueue(new Callback<List<Expression>>() {
                @Override
                public void onResponse(Call<List<Expression>> call, Response<List<Expression>> response) {
                    if (response.isSuccessful())
                        view.displayExpressions(response.body());
                    else
                        view.showError(context.getString(R.string.expression_error));
                }

                @Override
                public void onFailure(Call<List<Expression>> call, Throwable t) {
                    Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                    view.showError(context.getString(R.string.expression_error));
                }
            });
        }
    }

    private void initTopDictionary() {
        topDictionary = new HashMap<>();
        topDictionary.put(TopType.DAY, null);
        topDictionary.put(TopType.WEEK, null);
        topDictionary.put(TopType.ALLTIME, null);
    }
}
