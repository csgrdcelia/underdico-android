package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("users")
    Call<User> register(@Body User user);
}