package com.esgi.project.underdico.views.search;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

public interface SearchView {
    void assignViews();
    void setListeners();
    void displaySearchResult(List<Expression> expressions);
    void showError(String error);
    void goHome();
}
