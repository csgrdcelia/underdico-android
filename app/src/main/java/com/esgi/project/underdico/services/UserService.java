package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.models.User;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @POST("users")
    Call<User> register(@Body User user);

    @FormUrlEncoded
    @POST("users/token")
    Call<Token> login(@Field("username") String username, @Field("password") String password);

    @GET("users/{userId}")
    Call<User> getUser(@Header("Authorization") String token, @Path("userId") String id);

    @PATCH("users/{userId}")
    Call<User> updateUser(@Header("Authorization") String token, @Path("userId") String id, @Body User user);

    @Multipart
    @PUT("users/{userId}/avatar")
    Call<User> setProfilePicture(@Header("Authorization") String token, @Path("userId") String id, @Part MultipartBody.Part image);

}