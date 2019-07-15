package com.esgi.project.underdico.services;

import com.esgi.project.underdico.models.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RoomService {
    @GET("rooms")
    Call<List<Room>> getRooms();

    @GET("rooms")
    Call<List<Room>> getRoomsWhere(@Query("where") String where);

    @GET("rooms/private")
    Call<Room> getPrivateRoom(@Header("Authorization") String token, @Query("code") String code);

    @POST("rooms")
    Call<Room> createRoom(@Header("Authorization") String token, @Body Room room);
}
