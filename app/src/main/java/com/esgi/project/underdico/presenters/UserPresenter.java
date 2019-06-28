package com.esgi.project.underdico.presenters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.FileUtils;
import com.esgi.project.underdico.utils.LanguageManager;
import com.esgi.project.underdico.utils.PermissionManager;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.user.UserView;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UserPresenter {
    UserView view;
    User user;
    Context context;
    Activity parentActivity;

    private static final int STORAGE_PERMISSION = 100;

    public UserPresenter(UserView view, User user, Context context, Activity parentActivity) {
        this.view = view;
        this.user = user;
        this.context = context;
        this.parentActivity = parentActivity;

        initialize();
    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
        view.allowModification(user.getId().equals(Session.getCurrentToken().getUserId()));
        callUserInformation();
        retrieveProfilePicture();

    }

    public void processModifications(String language, Uri newProfilePicture) {

        if (newProfilePicture != null) {
            PermissionManager permissionManager = new PermissionManager(context, parentActivity);

            if(permissionManager.isPermittedTo(WRITE_EXTERNAL_STORAGE)) {
                uploadNewProfilePicture(newProfilePicture);
            } else {
                permissionManager.askPermissionTo(WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION);
                return;
            }
        }

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

    private void retrieveProfilePicture() {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<ResponseBody> call = service.getProfilePicture(Session.getCurrentToken().getValue(), user.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showProgress(false);
                if (response.isSuccessful()) {
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    view.setProfilePicture(bmp);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showProgress(false);
                view.showError(context.getString(R.string.error));
            }
        });
    }

    public void callUserInformation() {
        view.showProgress(true);
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<User> call = service.getUser(Session.getCurrentToken().getValue(), user.getId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() || response.body() != null) {
                    user = response.body();
                    view.displayUserInformation(user);
                } else {
                    view.showError(context.getString(R.string.error));
                }
                view.showProgress(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showProgress(false);
                view.showError(context.getString(R.string.error_network));
            }
        });
    }

    private void uploadNewProfilePicture(Uri profilePicture) {

        File file = new File(FileUtils.getPath(context, profilePicture));

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(context.getContentResolver().getType(profilePicture)),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<ResponseBody> call = service.setProfilePicture(Session.getCurrentToken().getValue(), user.getId(), body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showProgress(false);
                if (response.isSuccessful()) {
                    view.refresh();
                } else {
                    view.showError(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showProgress(false);
                view.showError(context.getString(R.string.error));
            }
        });
    }
}
