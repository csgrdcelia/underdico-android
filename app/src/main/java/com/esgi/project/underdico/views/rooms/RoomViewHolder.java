package com.esgi.project.underdico.views.rooms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.presenters.RoomPresenter;
import com.esgi.project.underdico.views.game.GameFragment;
import com.esgi.project.underdico.views.main.MainActivity;

public class RoomViewHolder extends RecyclerView.ViewHolder implements RoomView, View.OnClickListener {
    private Context context;
    private RoomPresenter presenter;

    private TextView roomNameTextView;
    private ImageView privateImageView;
    private TextView playersTextView;
    private ImageView flagImageView;


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
    public void setListeners() {
        itemView.setOnClickListener(this);
    }

    @Override
    public void displayRoom(Room room) {
        roomNameTextView.setText(room.getName());
        privateImageView.setVisibility(room.isPrivate() ? View.VISIBLE : View.GONE);
        playersTextView.setText(room.getPlayersIds().length + "/" + room.getMaxPlayers());
        flagImageView.setImageDrawable(context.getDrawable(room.getFlagImage()));
    }

    @Override
    public void onClick(View v) {
        presenter.roomIsClicked();
    }

    @Override
    public void redirectToGame(Room room) {
        MainActivity activity = (MainActivity) context;
        Fragment myFragment = GameFragment.newInstance(room);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }
}
