package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Vote;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExpressionService {
    @GET("words")
    Call<List<Expression>> getExpressions(@Header("Authorization") String token);

    @GET("words")
    Call<List<Expression>> getExpressionsWithFilter(@Header("Authorization") String token, @Query("where") String name);

    @GET("words?where=\"tags\":\"{tag}\"")
    Call<List<Expression>> getExpressionsByTag(@Header("Authorization") String token, @Path("tag") String tag);

    @GET("words/{wordId}")
    Call<Expression> getExpression(@Header("Authorization") String token, @Path("wordId") String id);

    @GET("words/daily")
    Call<Expression> getExpressionOfTheDay(@Header("Authorization") String token, @Query("locale") String locale);

    @GET("words/random")
    Call<Expression> getRandomExpression(@Header("Authorization") String token, @Query("locale") String locale);

    @POST("words")
    Call<Expression> saveExpression(@Header("Authorization") String token, @Body Expression expression);

    @POST("words/{wordId}/votes")
    Call<ResponseBody> vote(@Header("Authorization") String token, @Path("wordId") String id, @Body Vote vote);

    @PATCH("words/{wordId}/votes/{voteId}")
    Call<ResponseBody> updateVote(@Header("Authorization") String token, @Path("wordId") String wordId, @Path("voteId") String voteId, @Body Vote vote);

    @Multipart
    @PUT("words/{wordId}/audio")
    Call<ResponseBody> putAudio(@Header("Authorization") String token, @Path("wordId") String id, @Part MultipartBody.Part file);

    @GET("words/{wordId}/audio")
    Call<ResponseBody> getAudio(@Header("Authorization") String token, @Path("wordId") String id);


}
