package com.esgi.project.underdico.views.game;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.presenters.GamePresenter;
import com.esgi.project.underdico.utils.Session;

public class GameFragment extends Fragment implements GameView {
    private static final String PARAM_ROOM = "room";

    private GamePresenter presenter;
    Room room;
    private TextView roomNameTextView;
    private TextView definitionTextView;
    private TextView wordTextView;
    private TextView usernameTextView;
    private TextView pointsTextView;
    private EditText answerEditText;


    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(Room room) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_ROOM, room);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            room = (Room)getArguments().getSerializable(PARAM_ROOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new GamePresenter(getContext(), this, room);
    }

    @Override
    public void assignViews() {
        roomNameTextView = getView().findViewById(R.id.roomNameTextView);
        definitionTextView = getView().findViewById(R.id.definitionTextView);
        wordTextView = getView().findViewById(R.id.wordTextView);
        usernameTextView = getView().findViewById(R.id.usernameTextView);
        pointsTextView = getView().findViewById(R.id.pointsTextView);
        answerEditText = getView().findViewById(R.id.answerEditText);
    }

    @Override
    public void setListeners() {
        answerEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //TODO: send answer
                Toast.makeText(getContext(), answerEditText.getText(), Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(answerEditText.getApplicationWindowToken(), 0);
                answerEditText.setText("");
                return true;
            }
            return false;
        });
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRoomInformation(Room room) {
        roomNameTextView.setText(room.getName());
        usernameTextView.setText(Session.getCurrentUser().getUsername());
        //TODO: display users
    }
}
