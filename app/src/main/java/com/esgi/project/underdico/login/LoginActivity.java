package com.esgi.project.underdico.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.MainActivity;
import com.esgi.project.underdico.R;
import com.esgi.project.underdico.utils.Language;

public class LoginActivity extends AppCompatActivity implements LoginView {

    // UI references
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private Button mEmailSignInButton;
    private TextView tvRegister;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        AssignViews();

        mEmailView.requestFocus();

        mEmailSignInButton.setOnClickListener(view -> attemptLogin());
        tvRegister.setOnClickListener(null);

    }

    private void AssignViews() {
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mProgressView = findViewById(R.id.login_progress);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        presenter.handleLogin(email, password);
    }

    public void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void askUserToFillFields() {
        Toast.makeText(this, "Merci de renseigner tous les champs", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessfully() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFail() {
        Toast.makeText(this, "Les identifiants sont incorrects", Toast.LENGTH_SHORT).show();
    }

}

