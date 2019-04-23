package com.esgi.project.underdico;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewExpressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewExpressionFragment extends Fragment {

    RelativeLayout tagLayout;
    ImageButton addTag;
    EditText tagValue;
    List<Button> tags;
    View.OnClickListener tagsListener;

    public NewExpressionFragment() {
        // Required empty public constructor
    }

    public static NewExpressionFragment newInstance() {
        NewExpressionFragment fragment = new NewExpressionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tags = new ArrayList<>();
        assignViews();
        setTagListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_expression, container, false);
    }

    private void assignViews() {
        addTag = getView().findViewById(R.id.ibAddTag);
        tagLayout = getView().findViewById(R.id.layoutTags);
        tagValue = getView().findViewById(R.id.etTag);
    }

    private void setTagListener() {
        tagsListener = v -> {
            tags.remove(v);
            v.setVisibility(View.GONE);
        };

        addTag.setOnClickListener(v -> {
            String tag = tagValue.getText().toString();

            if(!tagExists(tag)) {
                Button button = new Button(getContext());
                int id = View.generateViewId();
                button.setId(id);
                button.setText(tag);
                button.setHeight(40);
                button.setOnClickListener(tagsListener);
                tagLayout.addView(button);
                if(tags.size() > 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();

                    if(tags.size() % 3 == 0) {
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                        params.addRule(RelativeLayout.BELOW, tags.get(tags.size() - 3).getId());
                    } else {
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                        params.addRule(RelativeLayout.RIGHT_OF, tags.get(tags.size() - 1).getId());
                        params.addRule(RelativeLayout.ALIGN_BASELINE, tags.get(tags.size() - 1).getId());
                    }
                }
            tags.add(button);
            } else {
                Toast.makeText(getContext(), "Ce tag a déjà été ajouté !", Toast.LENGTH_SHORT).show();
            }

        });
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



}
