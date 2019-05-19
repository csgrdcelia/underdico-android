package com.esgi.project.underdico.views.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.RegisterPresenter;
import com.esgi.project.underdico.views.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private final String ARG_USERNAME = "username";

    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;

    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this, getApplicationContext());

        assignViews();
        setListeners();
    }

    @Override
    public void assignViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
    }

    @Override
    public void setListeners() {
        registerButton.setOnClickListener(
                view -> presenter.handleRegister(
                        usernameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString()));
    }

    @Override
    public void showRegisterSuccess() {
        Toast.makeText(this,this.getString(R.string.register_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginView(String username) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(ARG_USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void askUserToFillFields() {
        Toast.makeText(this,this.getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
    }
}
