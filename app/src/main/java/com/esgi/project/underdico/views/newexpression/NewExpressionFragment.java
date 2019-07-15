package com.esgi.project.underdico.views.newexpression;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.esgi.project.underdico.views.home.HomeFragment;
import com.esgi.project.underdico.views.imagespinner.FlagSpinnerAdapter;
import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.presenters.NewExpressionPresenter;
import com.google.android.flexbox.FlexboxLayout;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewExpressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewExpressionFragment extends Fragment implements NewExpressionView, AdapterView.OnItemSelectedListener {

    NewExpressionPresenter presenter;

    private static final String EXPRESSION_ARG = "expression";

    private Expression expressionModel = null;

    private static final String STOP_TAG = "STOP";
    private static final String PLAY_TAG = "PLAY";
    private static final int PERMISSION_TO_RECORD = 100;

    // UI references
    FlexboxLayout tagLayout;
    ImageButton addTag;
    EditText expressionName;
    EditText expressionDefinition;
    EditText expressionExample;
    EditText tagValue;
    Button btnSend;
    Spinner flagSpinner;
    ImageButton ibRecordAudio;
    ImageButton ibPlayStopAudio;
    ImageButton ibDeleteRecord;

    String selectedLanguage = "fr";

    View.OnClickListener tagsListener;


    public NewExpressionFragment() {
        // Required empty public constructor
    }

    public static NewExpressionFragment newInstance() {
        return new NewExpressionFragment();
    }

    public static NewExpressionFragment newInstance(Expression expression) {
        Bundle args = new Bundle();
        args.putSerializable(EXPRESSION_ARG, expression);
        NewExpressionFragment fragment = new NewExpressionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null) {
            expressionModel = (Expression) this.getArguments().getSerializable(EXPRESSION_ARG);
        }
        return inflater.inflate(R.layout.fragment_new_expression, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new NewExpressionPresenter(this, getContext());

        if (expressionModel != null)
            display(expressionModel);
    }

    /**
     * Assign UI references
     */
    @Override
    public void assignViews() {
        addTag = getView().findViewById(R.id.ibAddTag);
        tagLayout = getView().findViewById(R.id.layoutTags);
        tagValue = getView().findViewById(R.id.etTag);
        expressionName = getView().findViewById(R.id.etExpressionName);
        expressionDefinition = getView().findViewById(R.id.etExpressionDefinition);
        expressionExample = getView().findViewById(R.id.etExample);
        btnSend = getView().findViewById(R.id.btnSend);
        ibRecordAudio = getView().findViewById(R.id.ibRecordAudio);
        ibPlayStopAudio = getView().findViewById(R.id.ibPlayStopAudio);
        ibDeleteRecord = getView().findViewById(R.id.ibDeleteRecord);

        flagSpinner = getView().findViewById(R.id.flagSpinner);
        flagSpinner.setAdapter(new FlagSpinnerAdapter(getContext()));
    }

    @Override
    public void setListeners() {
        tagsListener = v ->  {
            presenter.removeTag(v);
            addTag.setVisibility(View.VISIBLE);
        };

        addTag.setOnClickListener(v -> {
            presenter.addTag(tagValue.getText().toString());
            tagValue.setText("");
        });

        ibRecordAudio.setOnClickListener(v -> presenter.recordAudio(getContext()));
        ibPlayStopAudio.setOnClickListener(v -> {
            if(v.getTag().toString().equals(STOP_TAG))
                presenter.stopRecord();
            else if (v.getTag().toString().equals(PLAY_TAG))
                presenter.playRecord();
        });

        ibDeleteRecord.setOnClickListener(v -> presenter.deleteRecord());

        flagSpinner.setOnItemSelectedListener(this);

        btnSend.setOnClickListener(v -> attemptSend());
    }

    /**
     * Displays the given expression
     * Appears when view is launched from the expression view ( -> create another definition )
     */
    private void display(Expression expression) {
        expressionName.setText(expression.getWord());
    }

    /**
     * Attempts to send the expression
     */
    private void attemptSend() {
        String name = expressionName.getText().toString();
        String definition = expressionDefinition.getText().toString();
        String example = expressionExample.getText().toString();
        presenter.attemptSend(name, definition, example, selectedLanguage);
    }

    /**
     * Formats the tag button with the given tag value
     */
    @Override
    public Button createTagButton(String tag) {
        Button button = new Button(getContext());
        int id = View.generateViewId();
        button.setId(id);
        button.setText(tag);
        button.setHeight(40);
        button.setOnClickListener(tagsListener);
        tagLayout.addView(button);
        return button;
    }

    @Override
    public boolean checkPermissionToRecord() {
        int result = ContextCompat.checkSelfPermission(getContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(),
                RECORD_AUDIO);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void askPermissionToRecord() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_TO_RECORD);
    }

    @Override
    public void showIsRecording() {
        ibPlayStopAudio.setVisibility(View.VISIBLE);
        ibPlayStopAudio.setTag(STOP_TAG);
        ibPlayStopAudio.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
        ibPlayStopAudio.setImageResource(R.drawable.ic_stop);

        ibDeleteRecord.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecordIsStopped() {
        ibPlayStopAudio.setVisibility(View.VISIBLE);
        ibPlayStopAudio.setTag(PLAY_TAG);
        ibPlayStopAudio.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        ibPlayStopAudio.setImageResource(R.drawable.ic_play);

        ibDeleteRecord.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecordIsDeleted() {
        ibDeleteRecord.setVisibility(View.GONE);
        ibPlayStopAudio.setVisibility(View.GONE);
    }

    @Override
    public void showRecordFailed() {
        Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();
        ibPlayStopAudio.setVisibility(View.GONE);
    }

    @Override
    public void showTagLimitIsReached() {
        Toast.makeText(getContext(), getContext().getString(R.string.max_tag_reached), Toast.LENGTH_SHORT).show();
        addTag.setVisibility(View.GONE);
    }

    @Override
    public void createSuccessfully() {
        Toast.makeText(getContext(), getContext().getString(R.string.expression_created), Toast.LENGTH_LONG).show();
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = HomeFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = ((Pair<String,Integer>)flagSpinner.getAdapter().getItem(position)).first;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
