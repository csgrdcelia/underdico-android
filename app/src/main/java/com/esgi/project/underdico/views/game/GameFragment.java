package com.esgi.project.underdico.views.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.R;

public class GameFragment extends Fragment {
    private static final String PARAM_ROOM_ID = "roomid";

    private String roomId;


    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(String roomId) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ROOM_ID, roomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomId = getArguments().getString(PARAM_ROOM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }
}
