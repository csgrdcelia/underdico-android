package com.esgi.project.underdico.views.newexpression;

import android.widget.Button;

public interface NewExpressionView {
    void showNotValidNameError();
    void showTagExists();
    void showTagLimitIsReached();
    void showNotValidDefinitionError();
    void showIsRecording();
    void showRecordIsStopped();
    void showRecordFailed();
    void showRecordIsDeleted();
    boolean checkPermissionToRecord();
    void askPermissionToRecord();
    Button createTagButton(String tag);
    void createSuccessfully();
    void creationFailed();
}
