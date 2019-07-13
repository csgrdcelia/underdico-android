package com.esgi.project.underdico.views.game;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.models.Round;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.presenters.GamePresenter;
import com.esgi.project.underdico.utils.Session;

import java.util.List;

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
    private ConstraintLayout waitingHostConstraintLayout;
    private Button startRoomButton;
    private ProgressBar countdownProgressBar;

    private CountDownTimer countDownTimer;


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
        waitingHostConstraintLayout = getView().findViewById(R.id.waitingHostConstraintLayout);
        countdownProgressBar = getView().findViewById(R.id.countdownProgressBar);
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
    public void displayRound(Round round, boolean isMyTurn, String playerUsername) {
        Toast.makeText(getContext(), getContext().getString(R.string.game_your_turn) + " " + playerUsername, Toast.LENGTH_SHORT).show();
        startCountdownTimer();
        if (round != null) {
            definitionTextView.setText(round.getDefinition());
            wordTextView.setText(round.getObfuscatedWord());
        }
        usernameTextView.setText(playerUsername);
        if(isMyTurn)
            answerEditText.setVisibility(View.VISIBLE);
        else
            answerEditText.setVisibility(View.GONE);
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
        waitingHostConstraintLayout.setVisibility(View.INVISIBLE);
        startGameConstraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayWaitingGame(boolean owner) {
        wordConstraintLayout.setVisibility(View.GONE);
        answerConstraintLayout.setVisibility(View.INVISIBLE);
        waitingHostConstraintLayout.setVisibility(owner ? View.INVISIBLE : View.VISIBLE);
        startGameConstraintLayout.setVisibility(owner ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void displayTerminatedGame() {
        wordConstraintLayout.setVisibility(View.GONE);
        waitingHostConstraintLayout.setVisibility(View.INVISIBLE);
        startGameConstraintLayout.setVisibility(View.INVISIBLE);
        answerConstraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRoomInformation(Room room) {
        roomNameTextView.setText(room.getName());
        usernameTextView.setText(Session.getCurrentUser().getUsername());
    }

    @Override
    public void showTimeout(User player) {
        Toast.makeText(getContext(), getContext().getString(R.string.game_timeout) + " " + player.getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.leaveRoom();
        presenter.disconnect();
    }

    private void startCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countdownProgressBar.setProgress(0);
        countdownProgressBar.setMax(300);
        countDownTimer = new CountDownTimer(30000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                int max = countdownProgressBar.getMax();
                int secs = (int)(millisUntilFinished / 1000);
                countdownProgressBar.setProgress((int)(countdownProgressBar.getMax() - (millisUntilFinished / 100)));
            }

            @Override
            public void onFinish() {
                countdownProgressBar.setProgress(30);
            }
        }.start();
    }
}
