package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.AudioHelper;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.FileUtils;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.expression.DetailedExpressionView;
import com.esgi.project.underdico.views.expression.ExpressionView;
import com.esgi.project.underdico.views.home.HomeView;

import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpressionPresenter {
    HomeView parent;
    ExpressionView view;
    Expression expression;
    Context context;
    String audioFileName = null;

    public ExpressionPresenter(ExpressionView view, Expression expression, Context context, HomeView parent) {
        this.view = view;
        this.expression = expression;
        this.context = context;
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        updateView();
    }

    private void updateView() {
        view.displayExpression(expression);
        view.displayTags(expression.getTagArray());

        if (expression.userDownvoted())
            view.displayUserVote(false);
        else if (expression.userUpvoted()) {
            view.displayUserVote(true);
        }
    }


    public Expression getExpression() {
        return expression;
    }

    /**
     * Searches the expressions tagged with the given tag
     */
    public void searchExpressionsWithTag(String tag) {
        //TODO: call api to get expressions with tag or do the search here
        view.searchWithTag(tag);
    }

    /**
     * Checks if user can vote and if so, updates expression and view
     */
    public void tryVote(boolean voteType) {
        if (!hasAlreadyVotedOfType(voteType)) {
            vote(voteType);
        }
    }

    /**
     * User can't vote if he tries to downvote and already has downvoted, same with upvote
     */
    private boolean hasAlreadyVotedOfType(boolean type) {
        if (type)
            return expression.userUpvoted();
        else
            return expression.userDownvoted();
    }

    /**
     *
     */
    public void playAudio() {
        try {
            if (audioFileName == null)
                getAudio();
            else
                AudioHelper.play(audioFileName);
        } catch (IOException e) {
            view.showError(context.getString(R.string.error));
        }
    }

    private void getAudio() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);

        Call<ResponseBody> call = service.getAudio(Session.getCurrentToken().getValue(), expression.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        audioFileName = FileUtils.createRandomCacheFile(context, ".mpga");
                        try (FileOutputStream fos = new FileOutputStream(audioFileName)) {
                            fos.write(response.body().bytes());
                        }
                        playAudio();
                    } catch (IOException e) {
                        view.showError(context.getString(R.string.error));
                    }
                } else if (response.code() == Constants.HTTP_NOTFOUND) {
                    view.showError(context.getString(R.string.error_audio_not_found));
                } else {
                    view.showError(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showError(context.getString(R.string.error));
            }
        });
    }

    /**
     * Add the new vote and updates the expression
     */
    private void vote(boolean voteType) {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);

        Call<ResponseBody> call = null;
        if (expression.getUserVoteId() != null)
            call = service.updateVote(Session.getCurrentToken().getValue(), expression.getId(), expression.getUserVoteId(), new Vote(voteType));
        else
            call = service.vote(Session.getCurrentToken().getValue(), expression.getId(), new Vote(voteType));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    updateExpression();
                } else {
                    view.showError(context.getString(R.string.error_vote));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showError(context.getString(R.string.error_vote));
            }
        });
    }

    private void updateExpression() {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Expression> call = service.getExpression(Session.getCurrentToken().getValue(), expression.getId());
        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if (response.isSuccessful() || response.body() != null) {
                    expression = response.body();
                    updateView();
                    updateExpressionOfTheDay();
                } else {
                    view.showError(context.getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showError(context.getString(R.string.error));
            }
        });
    }

    /**
     * Check if the expression of the day corresponds to the current expression and if so, updates it
     */
    private void updateExpressionOfTheDay() {
        if (parent != null)
            if (parent.isExpressionOfTheDay(expression))
                parent.displayExpressionOfTheDay(expression);

    }

    /**
     * Run when user wants to create another definiton
     * Redirects to the new expression fragment and pre-fill somme fields
     */
    public void createOtherDefinition() {
        ((DetailedExpressionView) view).goToNewExpressionView(expression);
    }


}
