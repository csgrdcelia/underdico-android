package com.esgi.project.underdico.views.privacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.PrivacyPresenter;
import com.esgi.project.underdico.views.login.LoginActivity;

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
        deleteAccountImageView.setOnClickListener(v -> showDeletionConfirmationDialog());
    }

    private void showDeletionConfirmationDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.delete_account)
                .icon(getContext().getDrawable(R.drawable.ic_delete_forever))
                .positiveText(getContext().getString(R.string.confirm))
                .negativeText(getContext().getString(R.string.cancel))
                .onPositive((dialog, which) -> presenter.deleteAccount())
                .show();
    }

    @Override
    public void redirectToLoginPage() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
