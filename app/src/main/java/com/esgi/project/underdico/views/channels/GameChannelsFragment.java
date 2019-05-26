package com.esgi.project.underdico.views.channels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.R;

import java.util.ArrayList;
import java.util.List;


public class GameChannelsFragment extends Fragment {

    RecyclerView rcChannels;

    public GameChannelsFragment() {
        // Required empty public constructor
    }


    public static GameChannelsFragment newInstance() {
        GameChannelsFragment fragment = new GameChannelsFragment();
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
        rcChannels = getActivity().findViewById(R.id.rcChannels);
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        objects.add(new Object()); //TODO: get real list of channels
        rcChannels.setLayoutManager(new GridLayoutManager(getView().getContext(),1));
        rcChannels.setAdapter(new ChannelAdapter(objects));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_channels, container, false);
    }

}
