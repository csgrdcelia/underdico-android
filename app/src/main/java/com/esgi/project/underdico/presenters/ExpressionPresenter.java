package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.models.Vote;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.expression.DetailedExpressionView;
import com.esgi.project.underdico.views.expression.ExpressionView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpressionPresenter {
    ExpressionView view;
    Expression expression;
    Context context;

    public ExpressionPresenter(ExpressionView view, Expression expression, Context context) {
        this.view = view;
        this.expression = expression;
        this.context = context;
        initialize();
    }

    private void initialize() {
        view.displayExpression(expression);
        view.displayTags(expression.getTagArray());
        updateViewIfUserHasVoted();
    }

    /**
     * Searches the expressions tagged with the given tag
     */
    public void searchExpressionsWithTag(String tag) {
        //TODO: call api to get expressions with tag or do the search here
        view.searchWithTag(tag, Expression.getExpressionsList());
    }

    /**
     * If the user has already voted, related button is disabled
     */
    private void updateViewIfUserHasVoted() {
        Vote vote = expression.getVote(Session.getCurrentUser());
        if(vote != null) {
            view.displayAlreadyVoted(vote.getType());
        }
    }

    /**
     * Checks if user can vote and if so, updates expression and view
     */
    public void tryVote(boolean voteType) {
        if(!hasAlreadyVotedOfType(voteType)) {
            Vote vote = expression.getVote(Session.getCurrentUser());
            if(vote != null) {
                removeVote(vote, voteType);
            } else {
                vote(voteType);

            }
        }
    }

    /**
     * User can't vote if he tries to downvote and already has downvoted, same with upvote
     */
    private boolean hasAlreadyVotedOfType(boolean score) {
        Vote vote = expression.getVote(Session.getCurrentUser());
        if(vote != null && vote.getType() == score) {
            return true;
        }
        return false;
    }

    /**
     * Run when user wants to create another definiton
     * Redirects to the new expression fragment and pre-fill somme fields
     */
    public void createOtherDefinition() {
        ((DetailedExpressionView)view).goToNewExpressionView(expression);
    }

    /**
     * Removes vote
     */
    private void removeVote(Vote vote, boolean wantedVoteType) {
        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<ResponseBody> call = service.deleteVote(Session.getCurrentUser().getId(), vote.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    expression.removeVote(vote);
                    tryVote(wantedVoteType);
                }
                else {
                    view.showError(context.getString(R.string.vote_error));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showError(context.getString(R.string.vote_error));
            }
        });
    }

    /**
     * Add the new vote and updates the expression
     */
    private void vote(boolean score) {
        Vote vote = new Vote(score, Session.getCurrentUser(), expression);

        ExpressionService service = ApiInstance.getRetrofitInstance(context).create(ExpressionService.class);
        Call<Vote> call = service.addVote(Session.getCurrentUser().getId(), vote);
        call.enqueue(new Callback<Vote>() {
            @Override
            public void onResponse(Call<Vote> call, Response<Vote> response) {
                if(response.isSuccessful() || response.body() != null) {
                    Vote newVote = response.body();
                    expression.addVote(newVote);
                    view.setScore(expression.getScore());
                    view.displayAlreadyVoted(newVote.getType());
                }
                else {
                    view.showError(context.getString(R.string.vote_error));
                }
            }

            @Override
            public void onFailure(Call<Vote> call, Throwable t) {
                view.showError(context.getString(R.string.vote_error));
            }
        });
    }

}
