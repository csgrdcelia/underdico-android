package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExpressionService {
    @GET("words")
    Call<List<Expression>> getExpressions();

    @POST("words")
    Call<Expression> saveExpression(@Header("Authorization") String token, @Body Expression expression);

    @POST("accounts/{id}/votes")
    Call<Vote> addVote(@Path("id") String id, @Body Vote vote);

    @DELETE("accounts/{id}/votes/{fk}")
    Call<ResponseBody> deleteVote(@Path("id") String id, @Path("fk") String voteId);
}
