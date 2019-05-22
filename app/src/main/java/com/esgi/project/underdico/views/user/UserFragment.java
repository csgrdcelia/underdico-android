package com.esgi.project.underdico.views.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.presenters.UserPresenter;

public class UserFragment extends Fragment implements UserView {

    UserPresenter presenter;
    User user;
    private static final String USER_ARG = "user";

    TextView usernameView;
    TextView statusView;
    TextView karmaView;
    TextView scoreView;

    ImageButton modificationButton;
    ImageView languageImage;

    public UserFragment() {
        // Required empty public constructor
    }


    public static UserFragment newInstance(User user) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_ARG, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            user = (User)getArguments().getSerializable(USER_ARG);
            presenter = new UserPresenter(this, user);
        } else {
            showError(getContext().getString(R.string.error));
            redirectToHome();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void assignViews() {
        usernameView = getView().findViewById(R.id.tvUsername);
        statusView = getView().findViewById(R.id.tvStatus);
        karmaView = getView().findViewById(R.id.tvKarma);
        scoreView = getView().findViewById(R.id.tvGameScore);

        modificationButton = getView().findViewById(R.id.ibModification);
        languageImage = getView().findViewById(R.id.ivLanguage);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void displayUserInformation(User user) {
        usernameView.setText(user.getUsername());
        statusView.setText(user.getRole(getContext()));
        karmaView.setText(Integer.toString(user.getKarma()));
    }

    @Override
    public void allowModification(boolean allow) {
        modificationButton.setVisibility(allow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToHome() {

    }


}
