package com.esgi.project.underdico.views.game;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;

public class PlayerViewHolder extends RecyclerView.ViewHolder {
    private TextView usernameTextView;
    private TextView pointsTextView;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);

        assignViews();
    }

    private void assignViews() {
        usernameTextView = itemView.findViewById(R.id.usernameTextView);
        pointsTextView = itemView.findViewById(R.id.pointsTextView);
    }

    public void bind(User user) {
        usernameTextView.setText(user.getUsername());
        pointsTextView.setText(user.getCurrentScore() + " points");
    }
}
