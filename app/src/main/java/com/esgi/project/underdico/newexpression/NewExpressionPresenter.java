package com.esgi.project.underdico.newexpression;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class NewExpressionPresenter {
    NewExpressionView view;
    List<Button> tags;
    private static final int MAX_TAGS = 5;

    public NewExpressionPresenter(NewExpressionView view) {
        this.view = view;
        tags = new ArrayList<>();
    }

    public void attemptSend(String expressionName, String expressionDefinition) {

        if(!isValidExpressionName()) {
            view.showNotValidNameError();
        } else if (!isValidExpressionDefinition()) {
            view.showNotValidDefinitionError();
        } else {
            boolean success = createExpression(expressionName, expressionDefinition, getTagList());

            if(success) {
                view.createSuccessfully();
            } else {
                view.creationFailed();
            }
        }
    }

    public void addTag(String tag) {
        if(!tag.isEmpty()) {
            if(tagExists(tag))
            {
               view.showTagExists();
            } else {
                Button button = view.createTagButton(tag);
                tags.add(button);

                if(tags.size() == MAX_TAGS) {
                    view.showTagLimitIsReached();
                }
            }
        }
    }

    public void removeTag(View button) {
        tags.remove(button);
        button.setVisibility(View.GONE);
    }

    private boolean tagExists(String tag) {
        for (Button button : tags)
        {
            String currentValue = button.getText().toString();
            if (currentValue.equals(tag))
                return true;
        }
        return false;
    }

    private List<String> getTagList() {
        List<String> list = new ArrayList<>();
        for (Button button : tags) {
            list.add(button.getText().toString());
        }
        return list;
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
