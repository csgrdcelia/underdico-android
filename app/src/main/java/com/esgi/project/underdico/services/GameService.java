package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GameService {
    @GET("rooms")
    Call<List<Room>> getRooms();

    @POST("rooms")
    Call<Room> createRoom(@Header("Authorization") String token, @Body Room room);
}
