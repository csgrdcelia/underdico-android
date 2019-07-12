package com.esgi.project.underdico.views.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.project.underdico.views.main.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.LoginPresenter;
import com.esgi.project.underdico.views.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private final String ARG_USERNAME = "username";
    // UI references
    private EditText usernameView;
    private EditText passwordView;
    private View progressView;
    private Button emailSignInButton;
    private Button registerButton;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this, getApplicationContext());

        setUsernameIfPresent();
    }

    private void attemptLogin() {
        String email = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        presenter.handleLogin(email, password);
    }

    @Override
    public void setUsernameIfPresent() {
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            usernameView.setText(extras.getString(ARG_USERNAME));
    }

    @Override
    public void assignViews() {
        usernameView = findViewById(R.id.usernameEditText);
        usernameView.requestFocus();
        passwordView = findViewById(R.id.passwordEditText);
        progressView = findViewById(R.id.loginProgressView);
        emailSignInButton = findViewById(R.id.signInButton);
        registerButton = findViewById(R.id.registerButton);
    }

    @Override
    public void setListeners() {
        emailSignInButton.setOnClickListener(view -> attemptLogin());
        registerButton.setOnClickListener(view -> goToRegisterView());
    }

    @Override
    public void goToRegisterView() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void askUserToFillFields() {
        Toast.makeText(this,this.getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessfully() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(final boolean show) {
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

