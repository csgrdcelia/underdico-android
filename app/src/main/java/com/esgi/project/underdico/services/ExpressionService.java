package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Expression;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExpressionService {
    @GET("words")
    Call<List<Expression>> getExpressions();

    @POST("accounts/{id}/words")
    Call<Expression> saveExpression(@Path("id") String id, @Body Expression expression);
}
