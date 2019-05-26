package com.esgi.project.underdico.views.expression;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.models.Vote;

import java.util.List;

public interface ExpressionView {

    void displayExpression(Expression expression);
    void displayTags(String[] tags);
    void displayUserVote(boolean score);
    void searchWithTag(String tag);
    void redirectToUserPage(User user);
    void showError(String error);
}
