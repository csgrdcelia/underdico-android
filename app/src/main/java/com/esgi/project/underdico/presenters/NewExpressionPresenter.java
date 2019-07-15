package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.AudioHelper;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.newexpression.NewExpressionView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExpressionPresenter {
    NewExpressionView view;
    List<Button> tags;
    AudioHelper recorder;
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
    public void attemptSend(String expressionName, String expressionDefinition, String expressionExample, String locale) {
        if(!isValidExpressionName()) {
            view.showToast(context.getString(R.string.invalid_name));
        } else if (!isValidExpressionDefinition()) {
            view.showToast(context.getString(R.string.invalid_definition));
        } else {
            createExpression(expressionName, expressionDefinition, expressionExample, getTagList(), locale);
        }
    }

    /**
     * Calls API to save expression
     */
    private void createExpression(String expressionName, String expressionDefinition, String expressionExample, List<String> tags, String locale) {
        Expression expression = new Expression(expressionName, expressionDefinition, expressionExample, tags.toArray(new String[0]), locale);

        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Expression> call = service.saveExpression(Session.getCurrentToken().getValue(), expression);
        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if(response.isSuccessful()) {
                    if(recorder != null) {
                        saveAudio(response.body().getId());
                    }
                    view.createSuccessfully();
                }
                else {
                    view.showToast(context.getString(R.string.creation_failed));
                }
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {
                view.showToast(context.getString(R.string.creation_failed));
            }
        });
    }

    /**
     * Calls API to save audio
     */
    private void saveAudio(String expressionId) {

        File file = new File(recorder.getSavePath());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("audio/mpeg"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<ResponseBody> call = service.putAudio(Session.getCurrentToken().getValue(), expressionId, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()) {
                    view.showToast(context.getString(R.string.error_audio_save));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showToast(context.getString(R.string.error_audio_save));
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
                recorder = new AudioHelper(context);
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
                view.showToast(context.getString(R.string.tag_exists));
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
