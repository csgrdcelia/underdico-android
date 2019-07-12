package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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

import okhttp3.ResponseBody;
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
        retrieveProfilePicture();
        setLanguage(Session.getCurrentUser().getLocale());

    }

    public void callRandomExpression() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Expression> call = service.getRandomExpression(Session.getCurrentToken().getValue(), Session.getCurrentUser().getLocale());
        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if(response.isSuccessful())
                    if(response.body() != null)
                        view.displayRandomExpression(response.body());
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveProfilePicture() {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<ResponseBody> call = service.getProfilePicture(Session.getCurrentToken().getValue(), Session.getCurrentUser().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    view.setProfilePicture(bmp);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                //view.showToast(context.getString(R.string.error));
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
