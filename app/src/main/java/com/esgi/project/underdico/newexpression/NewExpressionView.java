package com.esgi.project.underdico.newexpression;

import android.widget.Button;

public interface NewExpressionView {
    void showNotValidNameError();
    void showTagExists();
    void showTagLimitIsReached();
    void showNotValidDefinitionError();
    Button createTagButton(String tag);
    void createSuccessfully();
    void creationFailed();
}
