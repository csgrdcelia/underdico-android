package com.esgi.project.underdico.views.privacy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.PrivacyPresenter;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PrivacyFragment extends Fragment implements PrivacyView {

    PrivacyPresenter presenter;

    ImageView downloadSummaryImageView;
    ImageView deleteAccountImageView;

    public PrivacyFragment() {
        // Required empty public constructor
    }

    public static PrivacyFragment newInstance() {
        PrivacyFragment fragment = new PrivacyFragment();
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
        this.presenter = new PrivacyPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacy, container, false);
    }

    @Override
    public void assignViews() {
        downloadSummaryImageView = getView().findViewById(R.id.downloadSummaryImageView);
        deleteAccountImageView = getView().findViewById(R.id.deleteAccountImageView);
    }

    @Override
    public void setListeners() {
        downloadSummaryImageView.setOnClickListener(v -> presenter.downloadSummary());
    }

    @Override
    public void downloadData() {

    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
