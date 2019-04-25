package com.esgi.project.underdico.newexpression;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.esgi.project.underdico.home.HomeFragment;
import com.esgi.project.underdico.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.Expression;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewExpressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewExpressionFragment extends Fragment implements NewExpressionView {

    NewExpressionPresenter presenter;

    private static final String EXPRESSION = "expression";
    private Expression expressionModel = null;
    // UI references
    RelativeLayout tagLayout;
    ImageButton addTag;
    EditText expressionName;
    EditText expressionDefinition;
    EditText tagValue;
    Button btnSend;

    List<Button> tags;
    View.OnClickListener tagsListener;


    public NewExpressionFragment() {
        // Required empty public constructor
    }

    public static NewExpressionFragment newInstance() {
        return new NewExpressionFragment();
    }

    public static NewExpressionFragment newInstance(Expression expression) {
        Bundle args = new Bundle();
        args.putSerializable(EXPRESSION, expression);
        NewExpressionFragment fragment = new NewExpressionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null) {
            expressionModel = (Expression) this.getArguments().getSerializable(EXPRESSION);
        }
        return inflater.inflate(R.layout.fragment_new_expression, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new NewExpressionPresenter(this);

        tags = new ArrayList<>();
        assignViews();

        tagsListener = v -> removeTag(v);
        addTag.setOnClickListener(v -> addTag());
        btnSend.setOnClickListener(v -> attemptSend());

        if (expressionModel != null)
            display(expressionModel);
    }

    private void display(Expression expression) {
        expressionName.setText(expression.getLabel());
    }

    private void attemptSend() {
        String name = expressionName.getText().toString();
        String definition = expressionDefinition.getText().toString();
        List<String> tags = getTagList();
        //TODO: add language
        //TODO: add audio
        presenter.attemptSend(name, definition, tags);
    }

    private void assignViews() {
        addTag = getView().findViewById(R.id.ibAddTag);
        tagLayout = getView().findViewById(R.id.layoutTags);
        tagValue = getView().findViewById(R.id.etTag);
        expressionName = getView().findViewById(R.id.etExpressionName);
        expressionDefinition = getView().findViewById(R.id.etExpressionDefinition);
        btnSend = getView().findViewById(R.id.btnSend);
    }

    private void removeTag(View button) {
        tags.remove(button);
        button.setVisibility(View.GONE);
    }

    private void addTag() {
        String tag = tagValue.getText().toString();

        if (!tagExists(tag)) {
            Button button = new Button(getContext());
            int id = View.generateViewId();
            button.setId(id);
            button.setText(tag);
            button.setHeight(40);
            button.setOnClickListener(tagsListener);
            tagLayout.addView(button);
            if (tags.size() > 0) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();

                if (tags.size() % 3 == 0) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                    params.addRule(RelativeLayout.BELOW, tags.get(tags.size() - 3).getId());
                } else {
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                    params.addRule(RelativeLayout.RIGHT_OF, tags.get(tags.size() - 1).getId());
                    params.addRule(RelativeLayout.ALIGN_BASELINE, tags.get(tags.size() - 1).getId());
                }
            }
            tags.add(button);
            tagValue.setText("");
        } else {
            Toast.makeText(getContext(), "Ce tag a déjà été ajouté !", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean tagExists(String tag) {
        for (Button button : tags)
        {
            String currentValue = button.getText().toString();
            if (currentValue.equals(tag))
                return true;
        }
        return false;
    }

    private List<String> getTagList() {
        List<String> list = new ArrayList<>();
        for (Button button : tags) {
            list.add(button.getText().toString());
        }
        return list;
    }

    @Override
    public void showNotValidNameError() {
        Toast.makeText(getContext(), "Le nom est invalide", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotValidDefinitionError() {
        Toast.makeText(getContext(), "La définition est invalide", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createSuccessfully() {
        Toast.makeText(getContext(), "Votre expression a bien été créée et attend d'être validée !", Toast.LENGTH_LONG).show();
        MainActivity activity = (MainActivity) getView().getContext();
        Fragment myFragment = HomeFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void creationFailed() {
        Toast.makeText(getContext(), "La création a échoué", Toast.LENGTH_SHORT).show();
    }
}
