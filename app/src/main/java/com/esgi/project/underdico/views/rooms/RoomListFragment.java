package com.esgi.project.underdico.views.rooms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.presenters.RoomListPresenter;
import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.views.newroom.NewRoomFragment;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;


public class RoomListFragment extends Fragment implements RoomListView {

    RoomListPresenter presenter;
    RecyclerView rcRooms;
    SpeedDialView speedDial;

    public RoomListFragment() {
        // Required empty public constructor
    }


    public static RoomListFragment newInstance() {
        RoomListFragment fragment = new RoomListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.presenter = new RoomListPresenter(getContext(), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_channels, container, false);
    }

    @Override
    public void assignViews() {
        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_create_room, R.drawable.ic_edit_white).setLabel(R.string.create_room).create());
        speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_join_private_room, R.drawable.ic_lock_white).setLabel(R.string.join_private_room).create());
        speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_search_room, R.drawable.ic_search).setLabel(R.string.search_room).create());
        rcRooms = getActivity().findViewById(R.id.rcChannels);
        configureRecyclerView();
    }

    @Override
    public void setListeners() {
        speedDial.setOnActionSelectedListener(speedDialActionItem -> {
            switch (speedDialActionItem.getId()) {
                case R.id.fab_create_room:
                    redirectToRoomCreation();
                    return false;
                case R.id.fab_join_private_room:
                    showToast("join");
                case R.id.fab_search_room:
                    showToast("search");
                default:
                    return false;
            }
        });
    }

    public void configureRecyclerView() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        rcRooms.setLayoutManager(new GridLayoutManager(getContext(),1));
        rcRooms.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void displayRooms(List<Room> rooms) {
        rcRooms.setAdapter(new RoomAdapter(rooms, getContext()));
    }

    @Override
    public void redirectToRoomCreation() {
        MainActivity activity = (MainActivity) getContext();
        Fragment myFragment = NewRoomFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
