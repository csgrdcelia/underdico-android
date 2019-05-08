package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExpressionService {
    @GET("/api/words")
    Call<List<Expression>> getExpressions();
}
