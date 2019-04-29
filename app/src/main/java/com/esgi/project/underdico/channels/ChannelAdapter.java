package com.esgi.project.underdico.channels;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.project.underdico.R;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    List<Object> objects;

    public ChannelAdapter(List<Object> objects) {
        this.objects = objects;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.channel, viewGroup, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder channelViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
