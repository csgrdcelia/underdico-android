package com.esgi.project.underdico.presenters;

import android.content.Context;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.services.RoomService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.newroom.NewRoomView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRoomPresenter {
    Context context;
    NewRoomView view;

    public NewRoomPresenter(Context context, NewRoomView view) {
        this.context = context;
        this.view = view;

        initializeView();
    }

    private void initializeView() {
        view.assignViews();
        view.setListeners();
    }

    public void createRoom(String roomName, int maxplayers, String locale, boolean privateRoom) {
        if(roomName.isEmpty()) {
            view.showToast(context.getString(R.string.room_name_not_found));
            return;
        }

        Room room = new Room(roomName, maxplayers, locale, privateRoom);

        RoomService service = ApiInstance.getRetrofitInstance(context).create(RoomService.class);
        Call<Room> call = service.createRoom(Session.getCurrentToken().getValue(), room);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful() && response.body() != null) {
                    if(room.isPrivate()) {
                        view.displayPrivateCode(response.body().getCode());
                    }
                    view.redirectToGame(response.body());
                } else if (response.code() == Constants.HTTP_CONFLICT) {
                    view.showToast(context.getString(R.string.room_name_exists));
                }
                else {
                    view.showToast(context.getString(R.string.creation_failed));
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                view.showToast(context.getString(R.string.error_network));
            }
        });
    }

}
