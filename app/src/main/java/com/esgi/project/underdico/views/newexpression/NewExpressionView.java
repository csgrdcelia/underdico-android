package com.esgi.project.underdico.views.newexpression;

import android.widget.Button;

import com.esgi.project.underdico.views.BaseView;

public interface NewExpressionView extends BaseView {
    void showTagLimitIsReached();
    void showIsRecording();
    void showRecordIsStopped();
    void showRecordFailed();
    void showRecordIsDeleted();
    boolean checkPermissionToRecord();
    void askPermissionToRecord();
    Button createTagButton(String tag);
    void createSuccessfully();

    void showError(String error);
}
