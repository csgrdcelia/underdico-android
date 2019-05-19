package com.esgi.project.underdico.views.expression;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;

import java.util.List;

public interface ExpressionView {

    void displayExpression(Expression expression);
    void displayTags(String[] tags);
    void displayAlreadyVoted(boolean score);
    void setScore(int vote);
    void searchWithTag(String tag, List<Expression> expressions);
    void showError(String error);
}