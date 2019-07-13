package com.esgi.project.underdico.views.rooms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder> {
    private Context context;
    private List<Room> rooms;

    public RoomAdapter(List<Room> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room, viewGroup, false);
        return new RoomViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder roomViewHolder, int i) {
        roomViewHolder.bind(rooms.get(i));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
