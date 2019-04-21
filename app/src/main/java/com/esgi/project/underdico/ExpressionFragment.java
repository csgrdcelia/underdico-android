package com.esgi.project.underdico;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpressionFragment extends Fragment {

    public ExpressionFragment() {
        // Required empty public constructor
    }

    public static ExpressionFragment newInstance() {
        ExpressionFragment fragment = new ExpressionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expression, container, false);
    }

}
