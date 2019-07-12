package com.esgi.project.underdico.presenters;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.services.UserService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.PermissionManager;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.views.privacy.PrivacyView;
import com.snatik.storage.Storage;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PrivacyPresenter {
    private static final int STORAGE_PERMISSION = 100;
    Context context;
    PrivacyView view;

    public PrivacyPresenter(Context context, PrivacyView view) {
        this.context = context;
        this.view = view;

        initializeView();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();

    }

    public void downloadSummary() {
        UserService service = ApiInstance.getRetrofitInstance(context).create(UserService.class);
        Call<ResponseBody> call = service.getSummary(Session.getCurrentToken().getValue(), Session.getCurrentUser().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() || response.body() != null) {
                    try {
                        createFile(response.body().string());
                    } catch(IOException e) {
                        view.showError(context.getString(R.string.error));
                    }
                } else {
                    view.showError(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showError(context.getString(R.string.error_network));
            }
        });
    }

    private void createFile(String content) {
        PermissionManager permissionManager = new PermissionManager(context, (MainActivity)context);

        if(permissionManager.isPermittedTo(WRITE_EXTERNAL_STORAGE)) {
            String fileName = "summary.txt";
            Storage storage = new Storage(context);
            String path = storage.getExternalStorageDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
            storage.createFile(path, content);

            DownloadManager downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
            downloadManager.addCompletedDownload(fileName,fileName, true, "text/plain", path, content.length(),true);
        } else {
            permissionManager.askPermissionTo(WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION);
            return;
        }
    }
}
