package com.esgi.project.underdico;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.models.Vote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ExpressionTest {
    Expression expression;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getScore_WasOnlyUpvoted_Returns3() {
        Expression expression = new Expression(
                null,
                null,
                null,
                null,
                new Vote[] {new Vote(1), new Vote(1), new Vote(1)},
                null, null);

        Assert.assertEquals(3, expression.getScore());
    }

    @Test
    public void getScore_WasOnlyDownvoted_ReturnsMinus3() {
        Expression expression = new Expression(
                null,
                null,
                null,
                null,
                new Vote[] {new Vote(-1), new Vote(-1), new Vote(-1)},
                null,
                null);

        Assert.assertEquals(-3, expression.getScore());
    }

    @Test
    public void getScore_WasDownvotedAndUpvoted_Returns3() {
        Expression expression = new Expression(
                null,
                null,
                null,
                null,
                new Vote[] {new Vote(1), new Vote(1), new Vote(1), new Vote(1), new Vote(-1)},
                null,
                null);

        Assert.assertEquals(3, expression.getScore());
    }

    @Test
    public void getCreatedAt_Format() {
        Expression expression = new Expression(
                null,
                null,
                null,
                null,
                null,
                new GregorianCalendar(2019, 0,1).getTime(),
                null);
        Assert.assertEquals("1 janvier 2019", expression.getCreatedAt());
    }

}
