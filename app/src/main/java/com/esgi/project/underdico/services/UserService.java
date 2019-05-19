package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Token;
import com.esgi.project.underdico.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @POST("users")
    Call<User> register(@Body User user);

    @FormUrlEncoded
    @POST("users/token")
    Call<Token> login(@Field("username") String username, @Field("password") String password);

}