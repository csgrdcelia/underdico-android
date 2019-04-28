package com.esgi.project.underdico.expression;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.models.Vote;

public class ExpressionPresenter {
    ExpressionView view;

    Expression expression;
    User user = new User("test"); // TODO: get real user

    public ExpressionPresenter(ExpressionView view, Expression expression) {
        this.view = view;
        this.expression = expression;

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
        Vote vote = expression.getVote(user);
        if(vote != null) {
            view.displayAlreadyVoted(vote.getType());
        }
    }

    /**
     * Checks if user can vote and if so, updates expression and view
     */
    public void tryVote(Vote.Type type) {
        Vote vote = expression.getVote(user);

        if(!hasAlreadyVoted(type)) {
            if(vote != null)
                expression.removeVote(vote);
            Vote newVote = vote(type);
            view.displayAlreadyVoted(newVote.getType());
            view.setScore(expression.getScore());
        }
    }

    /**
     * User can't vote if he tries to downvote and already has downvoted, same with upvote
     */
    private boolean hasAlreadyVoted(Vote.Type type) {
        Vote vote = expression.getVote(user);
        if(vote != null && vote.getType() == type) {
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
     * Add the new vote and updates the expression
     */
    private Vote vote(Vote.Type type) {
        Vote vote = new Vote(user, type == Vote.Type.DOWN ? -1 : 1);
        expression.addVote(vote);
        //TODO: call api to update expression
        return vote;
    }
}
