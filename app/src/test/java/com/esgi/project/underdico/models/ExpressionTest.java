package com.esgi.project.underdico.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTest {

    @Test
    public void shouldSimpleWord_resultInHiddenWord() {
        Expression expression = new Expression("Afrotrap", null, null, null);
        Assert.assertEquals("_ _ _ _ _ _ _ _", expression.getHiddenWord());
    }

    @Test
    public void shouldWordWithSpecialChar_resultInHiddenWord() {
        Expression expression = new Expression("Afro-trap", null, null, null);
        Assert.assertEquals("_ _ _ _ - _ _ _ _", expression.getHiddenWord());
    }

    @Test
    public void shouldWordWithSpace_resultInHiddenWord() {
        Expression expression = new Expression("Afro trap", null, null, null);
        Assert.assertEquals("_ _ _ _   _ _ _ _", expression.getHiddenWord());
    }
}