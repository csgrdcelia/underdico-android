package com.esgi.project.underdico.expression;

import com.esgi.project.underdico.models.Expression;

public interface DetailedExpressionView extends ExpressionView {
    void goToNewExpressionView(Expression expression);
    void goHome();
}
