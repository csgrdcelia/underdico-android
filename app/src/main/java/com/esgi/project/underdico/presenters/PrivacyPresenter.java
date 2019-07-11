package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.os.Environment;

import com.esgi.project.underdico.views.privacy.PrivacyView;

import java.io.File;

public class PrivacyPresenter {
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

    private void createFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "underdico_summary");
    }
}
