package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.top.TopView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        view.assignViews();
        view.setListeners();
        getExpressionTop(TopType.DAY);
    }

    public void getExpressionTop(TopType type) {

        if (topDictionary.get(type) != null) {
            view.displayExpressions(topDictionary.get(type));
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS\'Z\'");
        String json = "{\"$and\":[{\"$expr\":{\"$and\":[{\"$gte\":[\"$createdAt\",{\"$dateFromString\":{\"dateString\":\""+ sdf.format(getTopDate(type).getTime()) + "\"}}]},{\"$lte\":[\"$createdAt\",{\"$dateFromString\":{\"dateString\":\""+ sdf.format(calendar.getTime()) +"\"}}]}]}},{\"locale\":\"" + Session.getCurrentUser().getLocale() + "\"}]}";

        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<List<Expression>> call = service.getExpressionsWithFilterAndSort(Session.getCurrentToken().getValue(), json, "score,desc");
        call.enqueue(new Callback<List<Expression>>() {
            @Override
            public void onResponse(Call<List<Expression>> call, Response<List<Expression>> response) {
                if (response.isSuccessful())
                    view.displayExpressions(response.body());
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

    private Calendar getTopDate(TopType type) {
        Calendar c = Calendar.getInstance();
        if(type == TopType.DAY) {
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 0);
        } else if (type == TopType.WEEK) {
            c.setTime(new Date());
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        } else if (type == TopType.ALLTIME) {
            c.setTime(new Date(Long.MIN_VALUE));
        }
        return c;
    }

    private void initTopDictionary() {
        topDictionary = new HashMap<>();
        topDictionary.put(TopType.DAY, null);
        topDictionary.put(TopType.WEEK, null);
        topDictionary.put(TopType.ALLTIME, null);
    }
}
