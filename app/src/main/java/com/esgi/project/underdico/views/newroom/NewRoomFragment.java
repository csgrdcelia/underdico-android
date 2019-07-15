package com.esgi.project.underdico.views.newroom;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.presenters.NewRoomPresenter;
import com.esgi.project.underdico.views.game.GameFragment;
import com.esgi.project.underdico.views.imagespinner.FlagSpinnerAdapter;
import com.esgi.project.underdico.views.main.MainActivity;


public class NewRoomFragment extends Fragment implements NewRoomView,  AdapterView.OnItemSelectedListener {

    private NewRoomPresenter presenter;
    private NumberPicker maxPlayersPicker;
    private EditText roomNameEditText;
    private Spinner flagSpinner;
    private Switch privateRoomSwitch;
    private Button createButton;

    String selectedLanguage = "fr";

    public NewRoomFragment() {
        // Required empty public constructor
    }

    public static NewRoomFragment newInstance() {
        NewRoomFragment fragment = new NewRoomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.presenter = new NewRoomPresenter(getContext(), this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_room, container, false);
    }

    @Override
    public void assignViews() {
        maxPlayersPicker = getActivity().findViewById(R.id.npMaxPlayers);
        roomNameEditText = getActivity().findViewById(R.id.roomNameEditText);
        flagSpinner = getActivity().findViewById(R.id.flagSpinner);
        privateRoomSwitch = getActivity().findViewById(R.id.privateSwitch);
        createButton = getActivity().findViewById(R.id.createButton);

        maxPlayersPicker.setMinValue(1);
        maxPlayersPicker.setMaxValue(20);
        maxPlayersPicker.setWrapSelectorWheel(true);

        flagSpinner.setAdapter(new FlagSpinnerAdapter(getContext()));
    }

    @Override
    public void setListeners() {
        flagSpinner.setOnItemSelectedListener(this);
        createButton.setOnClickListener(v -> presenter.createRoom(roomNameEditText.getText().toString(), maxPlayersPicker.getValue(), selectedLanguage, privateRoomSwitch.isChecked()));
    }

    @Override
    public void displayPrivateCode(String code) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.access_code)
                .icon(getContext().getDrawable(R.drawable.ic_lock))
                .content(code)
                .contentGravity(GravityEnum.CENTER)
                .positiveText("OK")
                .show();
    }

    @Override
    public void redirectToGame(Room room) {
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = GameFragment.newInstance(room);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).commit();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = ((Pair<String,Integer>)flagSpinner.getAdapter().getItem(position)).first;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
