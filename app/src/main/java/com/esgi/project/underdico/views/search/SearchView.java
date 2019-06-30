package com.esgi.project.underdico.views.search;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.BaseView;

import java.util.List;

public interface SearchView extends BaseView {
    void displaySearchResult(List<Expression> expressions);
    void showError(String error);
    void goHome();
}
