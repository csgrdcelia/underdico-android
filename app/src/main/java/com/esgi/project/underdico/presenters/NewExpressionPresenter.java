package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.AudioRecorder;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.newexpression.NewExpressionView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExpressionPresenter {
    NewExpressionView view;
    List<Button> tags;
    AudioRecorder recorder;
    Context context;
    private static final int MAX_TAGS = 5;

    public NewExpressionPresenter(NewExpressionView view, Context context) {
        this.view = view;
        this.context = context;
        tags = new ArrayList<>();

        initialize();

    }

    private void initialize() {
        view.assignViews();
        view.setListeners();
    }

    /**
     * Checks if expression is valid and saves it
     */
    public void attemptSend(String expressionName, String expressionDefinition, String language) {
        //TODO: send language
        if(!isValidExpressionName()) {
            view.showNotValidNameError();
        } else if (!isValidExpressionDefinition()) {
            view.showNotValidDefinitionError();
        } else {
            createExpression(expressionName, expressionDefinition, getTagList());
        }
    }

    /**
     * Calls API to save expression
     */
    private void createExpression(String expressionName, String expressionDefinition, List<String> tags) {

        //TODO: save sound (String pathToAudio = recorder == null ? null : recorder.getSavePath();)
        //TODO: save language

        Expression expression = new Expression(expressionName, expressionDefinition, tags.toArray(new String[0]));

        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Expression> call = service.saveExpression(Session.getCurrentToken().getValue(), expression);
        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if(response.isSuccessful() || response.body() != null)
                    view.createSuccessfully();
                else
                    view.creationFailed();
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {
                view.creationFailed();
            }
        });
    }

    /**
     * Record audio
     */
    public void recordAudio(Context context) {
        if(!view.checkPermissionToRecord()) {
            view.askPermissionToRecord();
        } else {
            try {
                view.showIsRecording();
                recorder = new AudioRecorder(context);
                recorder.record();
            } catch (IOException e) {
                view.showRecordFailed();
            }
        }
    }

    /**
     * Record actions
     */

    public void stopRecord() {
        recorder.stop();
        view.showRecordIsStopped();
    }

    public void playRecord() {
        try {
            recorder.play();
        } catch(IOException e) {
            view.showRecordFailed();
        }
    }

    public void deleteRecord() {
        recorder.deleteRecord();
        recorder = null;
        view.showRecordIsDeleted();
    }

    /**
     * Checks if tag is valid and displays it
     */
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

    /**
     * Removes tag from the list
     */
    public void removeTag(View button) {
        tags.remove(button);
        button.setVisibility(View.GONE);
    }

    /**
     * Checks if given tag exists
     */
    private boolean tagExists(String tag) {
        for (Button button : tags)
        {
            String currentValue = button.getText().toString();
            if (currentValue.equals(tag))
                return true;
        }
        return false;
    }

    /**
     * Transform the Button tag list to String list
     */
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




}
