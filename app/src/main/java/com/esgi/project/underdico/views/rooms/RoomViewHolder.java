package com.esgi.project.underdico.views.rooms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.presenters.ExpressionPresenter;
import com.esgi.project.underdico.presenters.RoomPresenter;

public class RoomViewHolder extends RecyclerView.ViewHolder implements RoomView {
    Context context;
    RoomPresenter presenter;

    TextView roomNameTextView;
    ImageView privateImageView;
    TextView playersTextView;
    ImageView flagImageView;

    public RoomViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    public void bind(Room room) {
        presenter = new RoomPresenter(context, this, room);
    }

    @Override
    public void assignViews() {
        roomNameTextView = itemView.findViewById(R.id.tvRoomName);
        privateImageView = itemView.findViewById(R.id.ivPrivate);
        playersTextView = itemView.findViewById(R.id.tvPlayers);
        flagImageView = itemView.findViewById(R.id.ivLocale);
    }

    @Override
    public void displayRoom(Room room) {
        roomNameTextView.setText(room.getName());
        privateImageView.setVisibility(room.isPrivate() ? View.VISIBLE : View.GONE);
        playersTextView.setText(room.getPlayersIds().length + "/" + room.getMaxPlayers());
        flagImageView.setImageDrawable(context.getDrawable(room.getFlagImage()));
    }
}
