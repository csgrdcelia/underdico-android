package com.esgi.project.underdico.newexpression;

import java.util.List;

public class NewExpressionPresenter {
    NewExpressionView view;

    public NewExpressionPresenter(NewExpressionView view) {
        this.view = view;
    }

    public void attemptSend(String expressionName, String expressionDefinition, List<String> tags) {

        if(!isValidExpressionName()) {
            view.showNotValidNameError();
        } else if (!isValidExpressionDefinition()) {
            view.showNotValidDefinitionError();
        } else {
            boolean success = createExpression(expressionName, expressionDefinition, tags);

            if(success) {
                view.createSuccessfully();
            } else {
                view.creationFailed();
            }
        }

    }

    private boolean isValidExpressionName() {
        //TODO: set name requirements
        return true;
    }

    private boolean isValidExpressionDefinition() {
        //TODO: set definition requirements
        return true;
    }

    private boolean createExpression(String expressionName, String expressionDefinition, List<String> tags) {
        //TODO: call api
        return true;
    }


}
