package com.esgi.project.underdico.presenters;

import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.services.GameService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.views.rooms.RoomListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListPresenter {
    Context context;
    RoomListView view;

    public RoomListPresenter(Context context, RoomListView view) {
        this.context = context;
        this.view = view;

        initializeView();
    }

    public void initializeView() {
        view.assignViews();
        view.setListeners();

        displayRooms();
    }

    public void displayRooms() {
        GameService service = ApiInstance.getRetrofitInstance(context).create(GameService.class);
        Call<List<Room>> call = service.getRooms();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        view.displayRooms(response.body());
                } else {
                    view.showError(context.getString(R.string.rooms_error));
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e(Constants.NETWORK_ERROR, "\nCause: " + t.getCause() + "\nMessage: " + t.getMessage() + "\nLocalized Message: " + t.getLocalizedMessage());
                view.showError(context.getString(R.string.error_network));
            }
        });
    }

}
