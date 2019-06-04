package com.esgi.project.underdico.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.presenters.UserPresenter;
import com.esgi.project.underdico.utils.Constants;
import com.esgi.project.underdico.utils.LanguageManager;
import com.esgi.project.underdico.views.imagespinner.FlagSpinnerAdapter;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class UserFragment extends Fragment implements UserView, AdapterView.OnItemSelectedListener {

    UserPresenter presenter;
    User user;
    private static final String USER_ARG = "user";

    TextView usernameView;
    TextView statusView;
    TextView karmaView;
    TextView scoreView;

    ImageButton modificationButton;
    ImageButton confirmModificationButton;
    ImageView flagImage;

    Spinner flagSpinner;
    ProgressBar progressBar;

    String selectedLanguage = "fr";

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
            presenter = new UserPresenter(this, user, getContext());
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
        confirmModificationButton = getView().findViewById(R.id.ibConfirmModification);
        flagImage = getView().findViewById(R.id.ivLanguage);

        flagSpinner = getView().findViewById(R.id.flagSpinner);
        flagSpinner.setAdapter(new FlagSpinnerAdapter(getContext()));

        progressBar = getView().findViewById(R.id.progressBar);
    }

    @Override
    public void setListeners() {
        flagSpinner.setOnItemSelectedListener(this);
        modificationButton.setOnClickListener(v -> displayModificationView(true));
        confirmModificationButton.setOnClickListener(v -> presenter.processModifications(selectedLanguage));
    }

    @Override
    public void displayUserInformation(User user) {
        usernameView.setText(user.getUsername());
        statusView.setText(user.getRole(getContext()));
        karmaView.setText(Integer.toString(user.getKarma()));
        displayFlag(user.getLocale());
    }

    private void displayFlag(String locale) {
        List<Pair<String, Integer>> flags = Constants.flags;
        int image = 0;
        for (Pair<String, Integer> flag : flags) {
            if(flag.first.equals(locale)) {
                image = flag.second;
                break;
            }
        }
        if(image != 0) {
            flagImage.setImageDrawable(getContext().getDrawable(image));
        }
    }

    @Override
    public void allowModification(boolean allow) {
        modificationButton.setVisibility(allow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void displayModificationView(boolean display) {
        int basicDisplay;
        int modificationDisplay;

        selectedLanguage = user.getLocale();

        if(display) {
            basicDisplay = View.GONE;
            modificationDisplay = View.VISIBLE;
        } else {
            basicDisplay = View.VISIBLE;
            modificationDisplay = View.GONE;
        }
        modificationButton.setVisibility(basicDisplay);
        confirmModificationButton.setVisibility(modificationDisplay);

        flagImage.setVisibility(basicDisplay);
        flagSpinner.setVisibility(modificationDisplay);
    }

    @Override
    public void showProgress(boolean show) {
        if(show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToHome() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = ((Pair<String,Integer>)flagSpinner.getAdapter().getItem(position)).first;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void refresh() {
        getActivity().recreate();
    }

}
