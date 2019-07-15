package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.services.RoomService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.rooms.RoomListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListPresenter {
    Context context;
    RoomListView view;
    List<Room> rooms;

    public RoomListPresenter(Context context, RoomListView view) {
        this.context = context;
        this.view = view;

        initializeView();
        displayAllRooms();
    }

    public RoomListPresenter(Context context, RoomListView view, List<Room> rooms) {
        this.context = context;
        this.view = view;
        this.rooms = rooms;

        initializeView();
        view.displayRooms(rooms);
    }

    public void initializeView() {
        view.assignViews();
        view.setListeners();
    }

    public void displayAllRooms() {
        RoomService service = ApiInstance.getRetrofitInstance(context).create(RoomService.class);
        Call<List<Room>> call = service.getRooms();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        view.displayRooms(response.body());
                } else {
                    view.showToast(context.getString(R.string.rooms_error));
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showToast(context.getString(R.string.error_network));
            }
        });
    }

    public void searchRoom(String roomName) {
        RoomService service = ApiInstance.getRetrofitInstance(context).create(RoomService.class);
        Call<List<Room>> call = service.getRoomsWhere("{ \"name\": \""+ roomName +"\" }");
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        view.displaySearchResult(response.body());
                } else {
                    view.showToast(context.getString(R.string.rooms_error));
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showToast(context.getString(R.string.error_network));
            }
        });
    }

    public void searchPrivateRoom(String code) {
        RoomService service = ApiInstance.getRetrofitInstance(context).create(RoomService.class);
        Call<Room> call = service.getPrivateRoom(Session.getCurrentToken().getValue(), code);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Room room = response.body();
                    room.setCode(code);
                    view.redirectToGame(room);
                } else if(response.code() == Constants.HTTP_NOTFOUND) {
                    view.showToast(context.getString(R.string.access_code_error));
                } else {
                    view.showToast(context.getString(R.string.rooms_error));
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showToast(context.getString(R.string.error_network));
            }
        });
    }

}
