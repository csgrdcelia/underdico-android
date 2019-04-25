package com.esgi.project.underdico.expression;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;

public interface ExpressionView {

    void displayExpression(Expression expression);
    void displayAlreadyVoted(Vote.Type type);
    void goToNewExpressionView(Expression expression);
    void goHome();
    void setScore(int vote);
    void showAPIError();
}
