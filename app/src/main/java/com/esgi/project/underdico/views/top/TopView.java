package com.esgi.project.underdico.views.top;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.BaseView;

import java.util.List;

public interface TopView extends BaseView {
    void displayExpressions(List<Expression> expressions);
    void showError(String error);
}
