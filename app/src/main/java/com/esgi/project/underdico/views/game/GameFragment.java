package com.esgi.project.underdico.views.game;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.presenters.GamePresenter;
import com.esgi.project.underdico.utils.Session;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

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
    private RecyclerView playersRecyclerView;
    private ConstraintLayout wordConstraintLayout;
    private ConstraintLayout answerConstraintLayout;
    private ConstraintLayout startGameConstraintLayout;
    private ConstraintLayout waitingPlayerConstraintLayout;
    private Button startRoomButton;


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
        presenter = new GamePresenter(getContext(), getActivity(), this, room);
    }

    @Override
    public void assignViews() {
        roomNameTextView = getView().findViewById(R.id.roomNameTextView);
        definitionTextView = getView().findViewById(R.id.definitionTextView);
        wordTextView = getView().findViewById(R.id.wordTextView);
        usernameTextView = getView().findViewById(R.id.usernameTextView);
        pointsTextView = getView().findViewById(R.id.pointsTextView);
        answerEditText = getView().findViewById(R.id.answerEditText);
        playersRecyclerView = getView().findViewById(R.id.usersRecyclerView);
        wordConstraintLayout = getView().findViewById(R.id.wordConstraintLayout);
        answerConstraintLayout = getView().findViewById(R.id.answerConstraintLayout);
        startGameConstraintLayout = getView().findViewById(R.id.startGameConstraintLayout);
        waitingPlayerConstraintLayout = getView().findViewById(R.id.waitingPlayerConstraintLayout);
        startRoomButton = getView().findViewById(R.id.startButton);

        configureUsersRecyclerView();
    }

    @Override
    public void setListeners() {
        answerEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.sendAnswer(answerEditText.getText().toString());

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(answerEditText.getApplicationWindowToken(), 0);
                answerEditText.setText("");
                return true;
            }
            return false;
        });

        startRoomButton.setOnClickListener(v -> presenter.startRoom());
    }

    public void configureUsersRecyclerView() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.divider_10dp)
        );

        playersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        playersRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void displayPlayers(List<User> users) {
        playersRecyclerView.setAdapter(new PlayerAdapter(users));
    }

    @Override
    public void displayRound(Expression expression) {
        definitionTextView.setText(expression.getDefinition());
        //definitionTextView.setText(expression.getDefinition().replace(expression.getWord(), "*"));
        //wordTextView.setText(expression.getHiddenWord());
    }

    @Override
    public void displayProposalResult(boolean isCorrect, String username) {
        if(isCorrect) {
            Toast.makeText(getContext(), getContext().getString(R.string.game_good_proposal) + " " + username, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.game_wrong_proposal) + " " + username, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displayStartedGame() {
        wordConstraintLayout.setVisibility(View.VISIBLE);
        answerConstraintLayout.setVisibility(View.VISIBLE);
        waitingPlayerConstraintLayout.setVisibility(View.INVISIBLE);
        startGameConstraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayWaitingGame(boolean owner) {
        wordConstraintLayout.setVisibility(View.GONE);
        answerConstraintLayout.setVisibility(View.INVISIBLE);
        waitingPlayerConstraintLayout.setVisibility(owner ? View.INVISIBLE : View.VISIBLE);
        startGameConstraintLayout.setVisibility(owner ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void displayTerminatedGame() {
        wordConstraintLayout.setVisibility(View.GONE);
        waitingPlayerConstraintLayout.setVisibility(View.INVISIBLE);
        startGameConstraintLayout.setVisibility(View.INVISIBLE);
        answerConstraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRoomInformation(Room room) {
        roomNameTextView.setText(room.getName());
        usernameTextView.setText(Session.getCurrentUser().getUsername());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.leaveRoom();
        presenter.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disconnect();
    }
}
