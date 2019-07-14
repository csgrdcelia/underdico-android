package com.esgi.project.underdico.views.rooms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
    TextView titleTextView;

    private static final String SEARCH_KEY = "search";

    public RoomListFragment() {
        // Required empty public constructor
    }


    public static RoomListFragment newInstance() {
        RoomListFragment fragment = new RoomListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static RoomListFragment newInstance(List<Room> rooms) {
        RoomListFragment fragment = new RoomListFragment();
        Bundle args = new Bundle();
        args.putSerializable(SEARCH_KEY, new ArrayList<>(rooms));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(SEARCH_KEY)) {
            this.presenter = new RoomListPresenter(getContext(), this, (List<Room>)getArguments().getSerializable(SEARCH_KEY));
            titleTextView.setText(getActivity().getString(R.string.search_result));
        } else {
            this.presenter = new RoomListPresenter(getContext(), this);
            titleTextView.setText(getActivity().getString(R.string.join_room));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_channels, container, false);
    }

    @Override
    public void assignViews() {
        titleTextView = getActivity().findViewById(R.id.titleTextView);
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
                    showPrivateRoomDialog();
                    return false;
                case R.id.fab_search_room:
                    showSearchDialog();
                    return false;
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
    public void showSearchDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.search_room)
                .icon(getContext().getDrawable(R.drawable.ic_search_dark))
                .input(R.string.room_name, 0, false, (dialog, input) -> presenter.searchRoom(input.toString()))
                .positiveText("OK")
                .show();
    }

    @Override
    public void showPrivateRoomDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.join_private_room)
                .icon(getContext().getDrawable(R.drawable.ic_lock))
                .input(R.string.access_code, 0, false, (dialog, input) -> presenter.searchPrivateRoom(input.toString()))
                .positiveText("OK")
                .show();
    }

    @Override
    public void redirectToRoomCreation() {
        MainActivity activity = (MainActivity) getContext();
        Fragment myFragment = NewRoomFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void redirectToGame(Room room) {
        //TODO: redirect to game with code
    }

    @Override
    public void displaySearchResult(List<Room> rooms) {
        MainActivity activity = (MainActivity) getContext();
        Fragment myFragment = RoomListFragment.newInstance(rooms);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
