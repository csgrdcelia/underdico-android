package com.esgi.project.underdico.views.newexpression;

import android.widget.Button;

public interface NewExpressionView {
    void assignViews();
    void setListeners();
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
